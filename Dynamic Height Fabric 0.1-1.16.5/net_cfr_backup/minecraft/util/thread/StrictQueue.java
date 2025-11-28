/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.util.thread;

import org.jetbrains.annotations.Nullable;

public interface StrictQueue<T, F> {
    @Nullable
    public F pop();

    public boolean push(T var1);

    public boolean isEmpty();
}
