/* Copyright (C) Thomas Howe - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Thomas Howe <thomas@dc0d.com>, March 2015
 */

package com.dc0d.iiridarts.venture.client.item;

public class ItemStack {
	public short type;
	public int itemCount;
	public ItemStack(short type, int itemCount) {
		this.type = type;
		this.itemCount = itemCount;
	}
}
