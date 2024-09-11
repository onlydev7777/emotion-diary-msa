package com.emotion.emotiondiarydiary.config;

import io.github.resilience4j.core.ContextPropagator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextPropagator implements ContextPropagator<SecurityContext> {

  @Override
  public Supplier<Optional<SecurityContext>> retrieve() {
    return () -> Optional.ofNullable(SecurityContextHolder.getContext());
  }

  @Override
  public Consumer<Optional<SecurityContext>> copy() {
    return context -> SecurityContextHolder.setContext(context.orElse(null));
  }

  @Override
  public Consumer<Optional<SecurityContext>> clear() {
    return context -> SecurityContextHolder.clearContext();
  }
}
