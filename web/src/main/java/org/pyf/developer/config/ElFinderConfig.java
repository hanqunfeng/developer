package org.pyf.developer.config;


import org.pyf.developer.elfinder.command.CommandFactory;
import org.pyf.developer.elfinder.param.Node;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElFinderConfig {



    @Bean(name = "commandFactory")
    public CommandFactory getCommandFactory() {
        CommandFactory commandFactory = new CommandFactory();
        commandFactory.setClassNamePattern("org.pyf.developer.elfinder.command.%sCommand");
        return commandFactory;
    }


    @ConfigurationProperties("elfinder.home")
    @Bean(name = "homeNode")
    public Node homeNode(){
        Node node = new Node();
        return node;
    }

    @ConfigurationProperties("elfinder.share")
    @Bean(name = "shareNode")
    public Node shareNode(){
        Node node = new Node();
        return node;
    }

    @ConfigurationProperties("elfinder.shareall")
    @Bean(name = "shareallNode")
    public Node shareallNode(){
        Node node = new Node();
        return node;
    }


}
