package com.hkh.ai.agent.core.function;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ToolFunction implements Function {
    @Override
    public Object apply(Object o) {
        return null;
    }

    @NotNull
    @Override
    public Function andThen(@NotNull Function after) {
        return Function.super.andThen(after);
    }

    @NotNull
    @Override
    public Function compose(@NotNull Function before) {
        return Function.super.compose(before);
    }
}
