package walnoot.ld31;

import walnoot.libgdxutils.State;
import walnoot.libgdxutils.Transition;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class FadeState extends State {
	private State newState;
	private Color fadeColor;
	private Transition transition;
	
	public FadeState(State newState, Color fadeColor) {
		this(newState, fadeColor, 1f);
	}
	
	public FadeState(State newState, Color fadeColor, float time) {
		this(newState, fadeColor, new Transition.FadeTransition(time).updateEnd());
	}
	
	public FadeState(State newState, Color fadeColor, Transition transition) {
		this.newState = newState;
		this.fadeColor = fadeColor;
		this.transition = transition;
	}
	
	@Override
	public void update() {
		manager.transitionTo(newState, transition);
	}
	
	public void render(FrameBuffer target) {
		clearScreen(fadeColor);
	};
}
