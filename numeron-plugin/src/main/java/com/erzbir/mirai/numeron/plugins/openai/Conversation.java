package com.erzbir.mirai.numeron.plugins.openai;

import com.theokanning.openai.completion.chat.ChatMessage;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Erzbir
 * @Date: 2023/3/4 19:21
 */
public class Conversation implements List<ChatMessage> {
    private final LinkedList<ChatMessage> list = new LinkedList<>();
    private int size;
    private int limit = 2048;

    public Conversation(int limit) {
        this.limit = limit;
    }

    public List<ChatMessage> getList() {
        return list;
    }

    private boolean overLimit() {
        return size > limit;
    }

    private boolean overLimit(int len) {
        return size + len > limit;
    }

    private void reduce() {
        while (overLimit()) {
            remove(0);
        }
    }

    private void calculateTokens() {
        list.forEach(t -> size += t.getContent().length());
    }

    public int totalToken() {
        return size;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @NotNull
    @Override
    public Iterator<ChatMessage> iterator() {
        return list.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(ChatMessage chatMessage) {
        size += chatMessage.getContent().length();
        if (overLimit()) {
            reduce();
        }
        return list.add(chatMessage);
    }

    @Override
    public boolean remove(Object o) {
        size -= ((ChatMessage) o).getContent().length();
        return list.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends ChatMessage> c) {
        c.forEach(t -> size += t.getContent().length());
        if (overLimit()) {
            reduce();
        }
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends ChatMessage> c) {
        c.forEach(t -> size += t.getContent().length());
        if (overLimit()) {
            reduce();
        }
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        c.forEach(t -> size -= ((ChatMessage) t).getContent().length());
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        c.forEach(t -> size -= ((ChatMessage) t).getContent().length());
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        size = 0;
        list.clear();
    }

    @Override
    public ChatMessage get(int index) {
        return list.get(index);
    }

    @Override
    public ChatMessage set(int index, ChatMessage element) {
        size -= list.get(index).getContent().length() + element.getContent().length();
        return list.set(index, element);
    }

    @Override
    public void add(int index, ChatMessage element) {
        size += element.getContent().length();
        if (overLimit()) {
            reduce();
        }
        list.add(index, element);
    }

    @Override
    public ChatMessage remove(int index) {
        size -= list.get(index).getContent().length();
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<ChatMessage> listIterator() {
        return list.listIterator();
    }

    @NotNull
    @Override
    public ListIterator<ChatMessage> listIterator(int index) {
        return list.listIterator(index);
    }

    @NotNull
    @Override
    public List<ChatMessage> subList(int fromIndex, int toIndex) {
        List<ChatMessage> chatMessages = list.subList(fromIndex, toIndex);
        for (int i = 0, j = toIndex; i < fromIndex || j < list.size(); i++, j++) {
            if (i < fromIndex) {
                size -= list.get(i).getContent().length();
            }
            if (j < list.size()) {
                size -= list.get(j).getContent().length();
            }
        }
        return chatMessages;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
