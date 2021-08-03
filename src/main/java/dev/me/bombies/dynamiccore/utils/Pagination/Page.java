package dev.me.bombies.dynamiccore.utils.Pagination;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Page {
    @Getter
    private List<String> information = new ArrayList<>();


    public void addLine(String str) {
        information.add(str);
    }

    public void removeLine(int index) {
        information.remove(index);
    }

    public void removeLine(String str) {
        information.remove(str);
    }

    public void removeLines(int... index) {
        for (int i : index)
            information.remove(i);
    }

    public void removeLines(String... str) {
        for (String s : str)
            information.remove(s);
    }

    public void setLine(int index, String str) {
        information.add(index, str);
    }

    public String getLine(int index) {
        return information.get(index);
    }

    public List<String> getLines(int... index) {
        List<String> ret = new ArrayList<>();
        for (int i : index)
            ret.add(information.get(i));
        return ret;
    }

    public int getLineCount() {
        return information.size();
    }
}
