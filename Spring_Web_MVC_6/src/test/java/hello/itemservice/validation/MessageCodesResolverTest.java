package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageCodesResolverTest {
    
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    /**
     * 객체 오류의 경우 다음 순서로 2가지 생성
     * 1.: code + "." + object name
     * 2.: code
     */
    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    /**
     * 필드 오류의 경우 다음 순서로 4가지 메시지 코드 생성
     * 1.: code + "." + object name + "." + field
     * 2.: code + "." + field
     * 3.: code + "." + field type
     * 4.: code
     */
    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
        // BindingResult.rejectValue("itemName", "required"); -> 4가지 메시지 생성
        // new FiledError("item", "itemName", null, false, messageCodes, null, null);
    }
}
