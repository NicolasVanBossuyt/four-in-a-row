package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question implements Serializable {
    private List<String> clues;
    private String author, theme, answer;

    public Question() {
        clues = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            clues.add("");
        }
    }

    public Question(String author, String theme, String answer) {
        setAuthor(author);
        clues = new ArrayList<String>();
        setAnswer(answer);
        setTheme(theme);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void addClues(String a) {
        if (clues.size() <= 3) {
            if (!clues.contains(a)) {
                clues.add(a);
            }
        }
    }

    public void removeClues(String a) {
        clues.remove(a);
    }

    public List<String> getClues() {
        return new ArrayList<>(clues);
    }

    public void setClues(List<String> clues) {
        this.clues = clues;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isValid() {
        if (author == null || author.trim().isEmpty() || clues == null)
            return false;

        return clues.size() >= 1 && clues.size() <= 3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Question question = (Question) o;
        return Objects.equals(author, question.author) && Objects.equals(clues, question.clues)
                && Objects.equals(theme, question.theme) && Objects.equals(answer, question.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, clues, theme, answer);
    }

    @Override
    public String toString() {
        return "Questions{" + "author='" + author + '\'' + ", clues=" + clues + ", theme='" + theme + '\''
                + ", answer='" + answer + '\'' + '}';
    }

    public Question clone() {
        Question q = new Question(author, theme, answer);
        for (String clue : clues) {
            q.addClues(clue);
        }
        return q;
    }
}
