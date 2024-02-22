package com.erzbir.numeron.message;


import java.util.List;
import java.util.stream.Stream;

/**
 * @author Erzbir
 * @Data: 2024/2/7 23:09
 */
public interface MessageChain extends Message, Iterable<Message> {
    int size();

    boolean isEmpty();

    boolean contains(Message message);

    List<Message> toList();

    Stream<Message> toStream();

    MessageChain plus(Message message);
}
