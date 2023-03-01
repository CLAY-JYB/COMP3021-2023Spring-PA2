package hk.ust.comp3021.action;

import hk.ust.comp3021.resource.Paper;
import hk.ust.comp3021.person.User;
import java.util.*;
import java.util.function.Supplier;

public class SearchResearcherAction extends Action {
    public enum SearchResearcherKind {
        PAPER_WITHIN_YEAR,
        JOURNAL_PUBLISH_TIMES,
        KEYWORD_SIMILARITY,
    };

    private String searchFactorX;
    private String searchFactorY;
    private SearchResearcherKind kind;

    private final HashMap<String, List<Paper>> actionResult = new HashMap<String, List<Paper>>();

    public SearchResearcherAction(String id, User user, Date time, String searchFactorX, String searchFactorY, SearchResearcherKind kind) {
        super(id, user, time, ActionType.SEARCH_PAPER);
        this.searchFactorX = searchFactorX;
        this.searchFactorY = searchFactorY;
        this.kind = kind;
    }

    public String getSearchFactorX() {
        return searchFactorX;
    }

    public String getSearchFactorY() {
        return searchFactorY;
    }

    public void setSearchFactorX(String searchFactorX) {
        this.searchFactorX = searchFactorX;
    }

    public void setSearchFactorY(String searchFactorY) {
        this.searchFactorY = searchFactorY;
    }

    public SearchResearcherKind getKind() {
        return kind;
    }

    public void setKind(SearchResearcherKind kind) {
        this.kind = kind;
    }

    public HashMap<String, List<Paper>> getActionResult() {
        return actionResult;
    }

    public void appendToActionResult(String researcher, Paper paper) {
        List<Paper> paperList = this.actionResult.get(researcher);
        if (paperList == null) this.actionResult.put(researcher, new ArrayList<Paper>());
        paperList.add(paper);
    }

    // TODO: Implement the following common functional interfaces.
    // They are provided for implement the new researcher searching methods by Lambda expressions.

    /**
     * `searchFunc1` indicates the first searching criterion,
     *    i.e., Search researchers who publish papers more than X times in the recent Y years
     * @param null
     * @return `actionResult` that contains the relevant researchers
     */
    public Supplier<HashMap<String, List<Paper>>> searchFunc1 = () -> {
        return this.actionResult;
    };

    /**
     * `searchFunc2` indicates the second searching criterion,
     *    i.e., Search researchers whose papers published in the journal X have abstracts more than Y words.
     * @param null
     * @return `actionResult` that contains the relevant researchers
     */
    public Supplier<HashMap<String, List<Paper>>> searchFunc2 = () -> {
        return this.actionResult;
    };

    public static int getLevenshteinDistance(String str1, String str2) {
        return 0;
    }

    public static double getSimilarity(String str1, String str2) {
        return 0;
    }

    /**
     * `searchFunc2` indicates the third searching criterion
     *    i.e., Search researchers whoes keywords have more than similarity X as one of those of the researcher Y.
     * @param null
     * @return `actionResult` that contains the relevant researchers
     * PS: In this method, you are required to implement an extra method that calculates the Levenshtein Distance for
     *     two strings S1 and S2, i.e., the edit distance. Based on the Levenshtein Distance, you should calculate their
     *     similarity like `1 - levenshteinDistance / max(S1.length, S2.length)`.
     */
    public Supplier<HashMap<String, List<Paper>>> searchFunc3 = () -> {
        return this.actionResult;
    };

}
