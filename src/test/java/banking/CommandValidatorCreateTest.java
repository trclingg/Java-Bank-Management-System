package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class CommandValidatorCreateTest {
    private CommandValidator commandValidator;
    public static final String ACCOUNT_ID = "28282828";
    Bank bank;
    Savings savingsAccount;

    @BeforeEach
    void setUp(){
        bank = new Bank();
        commandValidator = new CommandValidator(bank);


    }

    @Test
    void valid_create_command_string() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 0.6");
        assertTrue(actual);
    }
    @Test
    void invalid_create_command() {
        boolean actual = commandValidator.validateCommand("Foobar savings 98765432 0.6");
        assertFalse(actual);
    }
    @Test
    void missing_create_command() {
        boolean actual = commandValidator.validateCommand("savings 98765432 0.6");
        assertFalse(actual);
    }



    @Test
    void extra_space_beginning() {
        boolean actual = commandValidator.validateCommand("    Create cd 98765432 1.2 1000.65");
        assertFalse(actual);
    }

    @Test
    void extra_space_middle() {
        boolean actual = commandValidator.validateCommand("Create       cd 98765432 1.2 1000.65");
        assertFalse(actual);
    }

    @Test
    void extra_space_end() {
        boolean actual = commandValidator.validateCommand("Create cd 98765432 1.2 1000.65     ");
        assertTrue(actual);
    }

    @Test
    void case_insensitive_action_command() {
        boolean actual = commandValidator.validateCommand("CReate savings 98765432 0.6");
        assertTrue(actual);
    }

    @Test
    void case_insensitive_account_types() {
        boolean actual = commandValidator.validateCommand("Create sAVinGs 98765432 0.6");
        assertTrue(actual);
    }
    @Test
    void typos_in_account_types() {
        boolean actual = commandValidator.validateCommand("Create favings 98765432 0.6");
        assertFalse(actual);
    }

    @Test
    void missing_account_types() {
        boolean actual = commandValidator.validateCommand("Create 98765432 0.6");
        assertFalse(actual);
    }

    @Test
    void valid_apr() {

        boolean actual = commandValidator.validateCommand("Create savings 98765432 0.65");
        assertTrue(actual);
    }

    @Test
    void apr_at_min_bound() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 0");
        assertTrue(actual);
    }

    @Test
    void apr_at_max_bound() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 10");
        assertTrue(actual);
    }

    @Test
    void negative_apr() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 -0.65");
        assertFalse(actual);
    }

    @Test
    void apr_not_in_range() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 15.3");
        assertFalse(actual);
    }

    @Test
    void missing_apr() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432");
        assertFalse(actual);
    }

    @Test
    void apr_not_in_double_form() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 two");
        assertFalse(actual);
    }
    @Test
    void account_id_has_more_than_8_digits() {
        boolean actual = commandValidator.validateCommand("Create savings 1234567890 0.6");
        assertFalse(actual);
    }

    @Test
    void account_id_has_less_than_8_digits() {
        boolean actual = commandValidator.validateCommand("Create savings 1234 0.6");
        assertFalse(actual);
    }

    @Test
    void invalid_form_of_account_id() {
        boolean actual = commandValidator.validateCommand("Create checking one123 0.01");
        assertFalse(actual);
    }

    @Test
    void missing_account_id() {
        boolean actual = commandValidator.validateCommand("Create savings 0.6");
        assertFalse(actual);
    }

    @Test
    void negative_account_id() {
        boolean actual = commandValidator.validateCommand("Create savings -1234567 0.6");
        assertFalse(actual);
    }

    @Test
    void valid_cd_amount() {
        boolean actual = commandValidator.validateCommand("Create cd 98765432 1.2 1200");
        assertTrue(actual);
    }

    @Test
    void cd_amount_with_decimal_trailing() {
        boolean actual = commandValidator.validateCommand("Create cd 98765432 1.2 1200.32");
        assertTrue(actual);
    }

    @Test
    void cd_amount_at_min_bound() {
        boolean actual = commandValidator.validateCommand("Create cd 98765432 1.2 1000");
        assertTrue(actual);
    }

    @Test
    void cd_amount_at_max_bound() {
        boolean actual = commandValidator.validateCommand("Create cd 98765432 1.2 10000");
        assertTrue(actual);
    }

    @Test
    void cd_amount_below_range() {
        boolean actual = commandValidator.validateCommand("Create cd 98765432 1.2 900");
        assertFalse(actual);
    }

    @Test
    void cd_amount_above_range() {
        boolean actual = commandValidator.validateCommand("Create cd 98765432 1.2 12000");
        assertFalse(actual);
    }

    @Test
    void missing_cd_amount() {
        boolean actual = commandValidator.validateCommand("Create cd 98765432 1.2");
        assertFalse(actual);
    }

    @Test
    void negative_cd_amount() {
        boolean actual = commandValidator.validateCommand("Create cd 98765432 1.2 -500");
        assertFalse(actual);
    }

    @Test
    void apr_can_have_two_decimals() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 0.65");
        assertTrue(actual);
    }

    @Test
    void cd_amount_can_have_two_decimals() {
        boolean actual = commandValidator.validateCommand("Create cd 98765432 1.2 1000.65");
        assertTrue(actual);
    }

    @Test
    void apr_can_have_one_decimal() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 0.6");
        assertTrue(actual);
    }

    @Test
    void cd_amount_can_have_one_decimal() {
        boolean actual = commandValidator.validateCommand("Create cd 98765432 1.2 1000.6");
        assertTrue(actual);
    }

    @Test
    void apr_cannot_have_more_than_two_decimal() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 0.6554");
        assertFalse(actual);
    }

    @Test
    void cd_amount_cannot_have_more_than_two_decimal() {
        boolean actual = commandValidator.validateCommand("Create cd 98765432 1.2 1000.668");
        assertFalse(actual);
    }

    @Test
    void account_id_is_not_unique() {
        savingsAccount = new Savings(ACCOUNT_ID,AccountTest.APR);
        bank.addAccount(ACCOUNT_ID, savingsAccount);
        boolean actual = commandValidator.validateCommand("Create checking 28282828 0.1");
        assertFalse(actual);
    }

    @Test
    public void command_with_one_argument_should_return_false() {
        boolean actual = commandValidator.validateCommand("create");
        assertFalse(actual);
    }

}
