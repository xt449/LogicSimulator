package com.github.xt449.logicsimulator;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;

import java.nio.IntBuffer;

/**
 * @author Jonathan Taclott (xt449 / BinaryBanana)
 * All Rights Reserved
 */
public class Font {

	public final STBTTFontinfo info;

	public final int ascent;
	public final int descent;
	public final int lineGap;

	public Font(String fileNamne) {
		info = STBTTFontinfo.create();

		final IntBuffer ascentPointer = BufferUtils.createIntBuffer(1);
		final IntBuffer descentPointer = BufferUtils.createIntBuffer(1);
		final IntBuffer lineGapPointer = BufferUtils.createIntBuffer(1);

		STBTruetype.stbtt_InitFont(info, ResourceLoader.readBytes("/fonts/" + fileNamne));
		STBTruetype.stbtt_GetFontVMetrics(info, ascentPointer, descentPointer, lineGapPointer);

		ascent = ascentPointer.get(0);
		descent = descentPointer.get(0);
		lineGap = lineGapPointer.get(0);
	}
}
