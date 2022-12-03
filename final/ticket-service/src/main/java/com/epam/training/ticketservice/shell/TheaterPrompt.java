package com.epam.training.ticketservice.shell;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class TheaterPrompt implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return new AttributedString("Csilli-villi filmszinhaz:~$ ");
    }
}
