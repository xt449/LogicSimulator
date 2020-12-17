package com.github.xt449.logicsimulator;

import javafx.scene.image.ImageView;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 */
public class BridgeComponent implements InstantComponent {

	private final boolean[] poweredFrom = new boolean[4];

	@Override
	public boolean isReceivingPower(int direction) {
		return poweredFrom[direction] || poweredFrom[Direction.getDirectionReversed(direction)];
	}

	@Override
	public boolean isSendingPower(int direction) {
		return poweredFrom[Direction.getDirectionReversed(direction)] && !poweredFrom[direction];
	}

	@Override
	public boolean hasInputFrom(int direction) {
		return true;
	}

	@Override
	public boolean hasOutputTo(int direction) {
		return true;
	}

	@Override
	public void tick(ComponentContainer container) {
		poweredFrom[0] = false;
		poweredFrom[1] = false;
		poweredFrom[2] = false;
		poweredFrom[3] = false;

		for(int direction = 0; direction < 4; direction++) {
			final ComponentContainer otherContainer = container.getRelativeComponentContainer(direction);
			if(otherContainer != null) {
				if(otherContainer.component != null) {
					if(otherContainer.component.isSendingPower(Direction.getDirectionReversed(direction))) {
						poweredFrom[direction] = true;

//						if(otherContainer.component instanceof InstantComponent) {
//							otherContainer.component.tick(otherContainer);
//							//return;
//						}
					}
				}
			}
		}

		final ComponentContainer containerUp = container.getRelativeComponentContainer(Direction.UP);
		final ComponentContainer containerDown = container.getRelativeComponentContainer(Direction.DOWN);
		final ComponentContainer containerLeft = container.getRelativeComponentContainer(Direction.LEFT);
		final ComponentContainer containerRight = container.getRelativeComponentContainer(Direction.RIGHT);


//		if(isSendingPower(Direction.UP)) {
//			final GridComponentContainer otherContainer = container.getRelativeGridSquare(Direction.UP);
		if(containerUp != null) {
			if(containerUp.component instanceof InstantComponent) {
				if(containerUp.component.hasInputFrom(Direction.DOWN)) {
					if(isSendingPower(Direction.UP)) {
						if(!containerUp.component.isReceivingPower(Direction.DOWN)) {
							containerUp.component.tick(containerUp);
						}
					} else {
						if(containerUp.component.isReceivingPower(Direction.DOWN)) {
							containerUp.component.tick(containerUp);
						}
					}
				}
			}
		}
//		}
//		if(isSendingPower(Direction.DOWN)) {
//			final GridComponentContainer otherContainer = container.getRelativeGridSquare(Direction.DOWN);
		if(containerDown != null) {
			if(containerDown.component instanceof InstantComponent) {
				if(containerDown.component.hasInputFrom(Direction.UP)) {
					if(isSendingPower(Direction.DOWN)) {
						if(!containerDown.component.isReceivingPower(Direction.UP)) {
							containerDown.component.tick(containerDown);
						}
					} else {
						if(containerDown.component.isReceivingPower(Direction.UP)) {
							containerDown.component.tick(containerDown);
						}
					}
				}
			}
		}
//		}
//		if(isSendingPower(Direction.LEFT)) {
//			final GridComponentContainer otherContainer = container.getRelativeGridSquare(Direction.LEFT);
		if(containerLeft != null) {
			if(containerLeft.component instanceof InstantComponent) {
				if(containerLeft.component.hasInputFrom(Direction.RIGHT)) {
					if(isSendingPower(Direction.LEFT)) {
						if(!containerLeft.component.isReceivingPower(Direction.RIGHT)) {
							containerLeft.component.tick(containerLeft);
						}
					} else {
						if(containerLeft.component.isReceivingPower(Direction.RIGHT)) {
							containerLeft.component.tick(containerLeft);
						}
					}
				}
			}
		}
//		}
//		if(isSendingPower(Direction.RIGHT)) {
//			final GridComponentContainer otherContainer = container.getRelativeGridSquare(Direction.RIGHT);
		if(containerRight != null) {
			if(containerRight.component instanceof InstantComponent) {
				if(containerRight.component.hasInputFrom(Direction.LEFT)) {
					if(isSendingPower(Direction.RIGHT)) {
						if(!containerRight.component.isReceivingPower(Direction.LEFT)) {
							containerRight.component.tick(containerRight);
						}
					} else {
						if(containerRight.component.isReceivingPower(Direction.LEFT)) {
							containerRight.component.tick(containerRight);
						}
					}
				}
			}
		}
//		}
	}

	@Override
	public Collection<ImageView> getImages(ComponentContainer container) {
		final ArrayDeque<ImageView> queue = new ArrayDeque<>(1);
		queue.add(new ImageView(Textures.getBridge(isReceivingPower(Direction.LEFT), isReceivingPower(Direction.UP))));

		// TODO : Debug
		/*for(int direction = 0; direction < poweredFrom.length; direction++) {
			if(poweredFrom[direction]) {
//				LogicSimulator.instance.prepareDrawTexture(Textures.getArrow(Direction.getDirectionReversed(direction)));
//				LogicSimulator.instance.drawTextureGridPosition(container.x, container.y);
			}
		}*/
		return queue;
	}
}
