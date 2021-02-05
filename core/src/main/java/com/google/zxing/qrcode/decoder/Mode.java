/*
 * Copyright 2007 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.qrcode.decoder;

/**
 * <p>See ISO 18004:2006, 6.4.1, Tables 2 and 3. This enum encapsulates the various modes in which
 * data can be encoded to bits in the QR code standard.</p>
 *
 * @author Sean Owen
 */

public class Mode {
	public enum ModeType {NUMERIC, ALPHANUMERIC, BYTE, ECI, KANJI}

	private final ModeType type;
	private final int[] characterCountBitsForVersions;
	private final int bits;

	private Mode(int[] characterCountBitsForVersions, int bits, ModeType type) {
		this.characterCountBitsForVersions = characterCountBitsForVersions;
		this.bits = bits;
		this.type = type;
	}

	public static Mode getMode(ModeType type){
		switch (type){
		case ALPHANUMERIC:
			return new Mode(new int[]{9, 11, 13}, 0x02, type);
		case BYTE:
			return new Mode(new int[]{8, 16, 16}, 0x04, type);
		case ECI:
			return new Mode(new int[]{0, 0, 0}, 0x07, type);
		case KANJI:
			return new Mode(new int[]{8, 10, 12}, 0x08, type);
		case NUMERIC:
		default:
			return new Mode(new int[]{10, 12, 14}, 0x01, type);
		}
	}

	/**
	 * @param version version in question
	 * @return number of bits used, in this QR Code symbol {@link Version}, to encode the
	 *         count of characters that will follow encoded in this Mode
	 */
	public int getCharacterCountBits(Version version) {
		int number = version.getVersionNumber();
		int offset;
		if (number <= 9) {
			offset = 0;
		} else if (number <= 26) {
			offset = 1;
		} else {
			offset = 2;
		}
		return characterCountBitsForVersions[offset];
	}

	public int getBits() {
		return bits;
	}

	/**
	 * Gets the type.
	 * @return the type.
	 */
	public ModeType getType() {
		return type;
	}


}