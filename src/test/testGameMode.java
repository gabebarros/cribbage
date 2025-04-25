package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.model.GameMode;


public class testGameMode {
	@Test
	public void testEnumDefinitions() {
		assertEquals(GameMode.CPU_EASY, GameMode.CPU_EASY);
		assertEquals(GameMode.CPU_HARD, GameMode.CPU_HARD);
		assertEquals(GameMode.PVP, GameMode.PVP);
	}
}
