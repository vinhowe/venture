package com.dc0d.iiridarts.venture.item;

public class ItemStack {
	public short type;
	public int itemCount;
	public ItemStack(short type, int itemCount) {
		this.type = type;
		this.itemCount = itemCount;
	}
}
