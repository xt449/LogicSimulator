package com.github.xt449.logicsimulator;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public class TestComponent implements GridComponentTest {

	@Override
	public boolean hasInputFrom(int direction) {
		return false;
	}

	@Override
	public boolean hasOutputTo(int direction) {
		return false;
	}

	@Override
	public boolean tick(GridComponentContainer container) {
		return false;
	}

	@Override
	public void tickEnd() {

	}

	@Override
	public State getState() {
		return null;
	}

	@Override
	public void render(GridComponentContainer container) {

	}

	static class State implements GridComponentTest.State {

		@Override
		public boolean isReceivingPower(int direction) {
			return false;
		}

		@Override
		public boolean isSendingPower(int direction) {
			return false;
		}
	}
}
