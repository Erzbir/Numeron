package com.erzbir.numeron.message;


import java.util.List;
import java.util.stream.Stream;

/**
 * @author Erzbir
 * @Data: 2024/2/8 00:07
 */
public interface AggregatedMessageReceipt extends MessageReceipt, Iterable<SingleMessageReceipt> {
    int size();

    SingleMessageReceipt get(int index);

    List<SingleMessageReceipt> toList();

    Stream<SingleMessageReceipt> toStream();


    @Override
    default void delete() {
        for (SingleMessageReceipt messageReceipt : this) {
            messageReceipt.delete();
        }
    }
}
