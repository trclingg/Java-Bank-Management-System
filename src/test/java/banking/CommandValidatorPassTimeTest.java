package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorPassTimeTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void valid_pass_time_command() {
		boolean actual = commandValidator.validateCommand("pass 1");
		assertTrue(actual);
	}

	@Test
	void pass_time_command_has_less_than_2_arguments_is_invalid() {
		boolean actual = commandValidator.validateCommand("pass");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_has_more_than_2_arguments_is_invalid() {
		boolean actual = commandValidator.validateCommand("pass 10 hi");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_has_to_have_pass_command() {
		boolean actual = commandValidator.validateCommand("10");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_is_case_insensitive() {
		boolean actual = commandValidator.validateCommand("PaSs 10");
		assertTrue(actual);
	}

	@Test
	void pass_time_command_cannot_have_typos() {
		boolean actual = commandValidator.validateCommand("Passing 10");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_cannot_have_space_at_the_beginning() {
		boolean actual = commandValidator.validateCommand("  Pass 10");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_cannot_have_extra_space_at_the_beginning() {
		boolean actual = commandValidator.validateCommand("  Pass 10");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_can_have_space_at_the_end() {
		boolean actual = commandValidator.validateCommand("pass 10  ");
		assertTrue(actual);
	}

	@Test
	void pass_time_command_accepts_time_at_max_value() {
		boolean actual = commandValidator.validateCommand("pass 60");
		assertTrue(actual);
	}

	@Test
	void pass_time_command_cannot_accept_time_larger_than_max_value() {
		boolean actual = commandValidator.validateCommand("pass 61");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_cannot_accept_time_negative() {
		boolean actual = commandValidator.validateCommand("pass -1");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_accepts_time_at_min_value() {
		boolean actual = commandValidator.validateCommand("pass 1");
		assertTrue(actual);
	}

	@Test
	void pass_time_command_cannot_accept_time_zero() {
		boolean actual = commandValidator.validateCommand("pass 0");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_cannot_accept_time_not_digit() {
		boolean actual = commandValidator.validateCommand("pass one");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_cannot_accept_time_not_integer() {
		boolean actual = commandValidator.validateCommand("pass 2.2");
		assertFalse(actual);
	}
}
