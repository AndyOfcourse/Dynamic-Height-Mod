package net.minecraft;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifierDefault;
import org.jetbrains.annotations.NotNull;

@NotNull
@TypeQualifierDefault({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodsReturnNonnullByDefault {
}
