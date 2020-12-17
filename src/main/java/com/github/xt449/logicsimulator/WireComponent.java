package com.github.xt449.logicsimulator;

import javafx.scene.image.ImageView;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class WireComponent implements InstantComponent {

	private boolean powered;

	private final boolean[] poweredFrom = new boolean[4];

	@Override
	public boolean isReceivingPower(int direction) {
		return poweredFrom[direction];
	}

	@Override
	public boolean isSendingPower(int direction) {
		return powered && !poweredFrom[direction];
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
		powered = false;
		poweredFrom[0] = false;
		poweredFrom[1] = false;
		poweredFrom[2] = false;
		poweredFrom[3] = false;

		for(int direction = 0; direction < 4; direction++) {
			final ComponentContainer otherContainer = container.getRelativeComponentContainer(direction);
			if(otherContainer != null) {
				if(otherContainer.component != null) {
					if(otherContainer.component.isSendingPower(Direction.getDirectionReversed(direction))) {
						powered = true;
						poweredFrom[direction] = true;

						/*if(square.component instanceof InstantComponent) {
							((InstantComponent) square.component).backtrack(square);
						}*/
					}
				}
			}
		}

		final ComponentContainer containerUp = container.getRelativeComponentContainer(Direction.UP);
		final ComponentContainer containerDown = container.getRelativeComponentContainer(Direction.DOWN);
		final ComponentContainer containerLeft = container.getRelativeComponentContainer(Direction.LEFT);
		final ComponentContainer containerRight = container.getRelativeComponentContainer(Direction.RIGHT);

		if(powered) {
			if(containerUp != null) {
				if(containerUp.component instanceof InstantComponent) {
					if(containerUp.component.hasInputFrom(Direction.DOWN)) {
						if(!containerUp.component.isSendingPower(Direction.DOWN)) {
							if(!containerUp.component.isReceivingPower(Direction.DOWN)) {
								containerUp.component.tick(containerUp);
							}
						}
					}
				}
			}

			if(containerDown != null) {
				if(containerDown.component instanceof InstantComponent) {
					if(containerDown.component.hasInputFrom(Direction.UP)) {
						if(!containerDown.component.isSendingPower(Direction.UP)) {
							if(!containerDown.component.isReceivingPower(Direction.UP)) {
								containerDown.component.tick(containerDown);
							}
						}
					}
				}
			}

			if(containerLeft != null) {
				if(containerLeft.component instanceof InstantComponent) {
					if(containerLeft.component.hasInputFrom(Direction.RIGHT)) {
						if(!containerLeft.component.isSendingPower(Direction.RIGHT)) {
							if(!containerLeft.component.isReceivingPower(Direction.RIGHT)) {
								containerLeft.component.tick(containerLeft);
							}
						}
					}
				}
			}

			if(containerRight != null) {
				if(containerRight.component instanceof InstantComponent) {
					if(containerRight.component.hasInputFrom(Direction.LEFT)) {
						if(!containerRight.component.isSendingPower(Direction.LEFT)) {
							if(!containerRight.component.isReceivingPower(Direction.LEFT)) {
								containerRight.component.tick(containerRight);
							}
						}
					}
				}
			}
		} else {
			if(containerUp != null) {
				if(containerUp.component instanceof InstantComponent) {
					if(containerUp.component.hasInputFrom(Direction.DOWN)) {
						//if(containerUp.component.isSendingPower(Direction.DOWN))
						if(containerUp.component.isReceivingPower(Direction.DOWN)) {
							containerUp.component.tick(containerUp);
						}
					}
				}
			}

			if(containerDown != null) {
				if(containerDown.component instanceof InstantComponent) {
					if(containerDown.component.hasInputFrom(Direction.UP)) {
						//if(containerDown.component.isSendingPower(Direction.UP))
						if(containerDown.component.isReceivingPower(Direction.UP)) {
							containerDown.component.tick(containerDown);
						}
					}
				}
			}

			if(containerLeft != null) {
				if(containerLeft.component instanceof InstantComponent) {
					if(containerLeft.component.hasInputFrom(Direction.RIGHT)) {
						//if(containerLeft.component.isSendingPower(Direction.RIGHT))
						if(containerLeft.component.isReceivingPower(Direction.RIGHT)) {
							containerLeft.component.tick(containerLeft);
						}
					}
				}
			}

			if(containerRight != null) {
				if(containerRight.component instanceof InstantComponent) {
					if(containerRight.component.hasInputFrom(Direction.LEFT)) {
						//if(containerRight.component.isSendingPower(Direction.LEFT))
						if(containerRight.component.isReceivingPower(Direction.LEFT)) {
							containerRight.component.tick(containerRight);
						}
					}
				}
			}
		}
	}

	@Override
	public Collection<ImageView> getImages(ComponentContainer container) {
		final ArrayDeque<ImageView> queue = new ArrayDeque<>(5);

		for(int direction = 0; direction < 4; direction++) {
			final Component component = container.getRelativeComponent(direction);
			if(component != null) {
				if(component.hasIO(Direction.getDirectionReversed(direction))) {
					queue.add(new ImageView(powered ? Textures.getPoweredWire(direction) : Textures.getWire(direction)));
				}
			}
		}

		queue.add(new ImageView(powered ? Textures.WIRE_CENTER_POWERED : Textures.WIRE_CENTER));

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
