package hk.ust.comp3021.action;

import hk.ust.comp3021.resource.Paper;
import hk.ust.comp3021.person.User;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
     * `searchFunc1` indicates the first searching criteria,
     *    i.e., Search researchers who publish papers more than X times in the recent Y years
     * @param null
     * @return `actionResult` that contains the relevant researchers
     */
    public Supplier<HashMap<String, List<Paper>>> searchFunc1 = () -> {
        int paperNum = Integer.parseInt(this.searchFactorX);
        int yearWithin = Integer.parseInt(this.searchFactorY);
        this.actionResult.entrySet().forEach(entry -> {
            List<Paper> newPaperList = entry.getValue().stream()
                    .filter(paper -> paper.getYear() + yearWithin >= 2023)
                    .collect(Collectors.toList());
            entry.getValue().clear();
            entry.getValue().addAll(newPaperList);
        });
        List<Map.Entry<String, List<Paper>>>temp = this.actionResult.entrySet().stream()
                .filter(entry -> entry.getValue().size() > paperNum)
                .collect(Collectors.toList());
        this.actionResult.clear();
        temp.stream().forEach(entry -> {
            this.actionResult.put(entry.getKey(), entry.getValue());
        });
        return this.actionResult;
    };

    /**
     * `searchFunc2` indicates the second searching criteria,
     *    i.e., Search researchers whose papers published in the journal X have abstracts more than Y words.
     * @param null
     * @return `actionResult` that contains the relevant researchers
     */
    public Supplier<HashMap<String, List<Paper>>> searchFunc2 = () -> {
        String journalName = this.searchFactorX;
        int abstractWordCnt = Integer.parseInt(this.searchFactorY);
        this.actionResult.entrySet().forEach(entry -> {
            List<Paper> newPaperList = entry.getValue().stream()
                    .filter(paper -> paper.getJournal() == journalName && paper.getAbsContent().length() > abstractWordCnt)
                    .collect(Collectors.toList());
            entry.getValue().clear();
            entry.getValue().addAll(newPaperList);
        });
        return this.actionResult;
    };

    public static int getLevenshteinDistance(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[][] editMatrix = new int[m + 1][n + 1];
        IntStream.range(1, editMatrix.length).forEach(idx -> {
            editMatrix[idx][0] = idx;
        });
        IntStream.range(1, editMatrix[0].length).forEach(idx -> {
            editMatrix[0][idx] = idx;
        });

        IntStream.range(1, editMatrix.length).forEach(idx1 -> {
            IntStream.range(1, editMatrix[idx1].length).forEach(idx2 -> {
                int cost = str1.charAt(idx1 - 1) == str2.charAt(idx2 - 1) ? 0: 1;
                int minResult = Integer.min(editMatrix[idx1 - 1][idx2] + 1, editMatrix[idx1][idx2 - 1] + 1);
                editMatrix[idx1][idx2] = Integer.min(minResult, editMatrix[idx1 - 1][idx2 - 1] + cost);
            });
        });

        return editMatrix[m][n];
    }

    public static double getSimilarity(String str1, String str2) {
        int levenshteinDistance = getLevenshteinDistance(str1, str2);
        return 1 - levenshteinDistance * 1.0 / Math.max(str1.length(), str2.length());
    }

    /**
     * `searchFunc2` indicates the third searching critiria
     *    i.e., Search researchers whoes keywords have more than similarity X as one of those of the researcher Y.
     * @param null
     * @return `actionResult` that contains the relevant researchers
     * PS: In this method, you are required to implement an extra method that calculates the Levenshtein Distance for
     *     two strings S1 and S2, i.e., the edit distance. Based on the Levenshtein Distance, you should calculate their
     *     similarity like `1 - levenshteinDistance / max(S1.length, S2.length)`.
     */
    public Supplier<HashMap<String, List<Paper>>> searchFunc3 = () -> {
        double similarityThreshold = Double.parseDouble(this.searchFactorX);
        String researcherName = this.searchFactorY;
        if (!this.actionResult.containsKey(researcherName)) {
            this.actionResult.clear();
            return this.actionResult;
        }
        ArrayList<Paper> researcherPapers = (ArrayList<Paper>) this.actionResult.get(researcherName);
        this.actionResult.entrySet().forEach(entry -> {
            List<Paper> newPaperList = entry.getValue().stream()
                    .filter(paperTest -> {
                        String keywordTest = String.join(",", paperTest.getKeywords());
                        long cnt = researcherPapers.stream().filter(paper -> {
                            return getSimilarity(keywordTest, String.join(",", paper.getKeywords())) >= similarityThreshold;
                        }).count();
                        return cnt >= 1;
                    })
                    .collect(Collectors.toList());
            entry.getValue().clear();
            entry.getValue().addAll(newPaperList);
        });
        this.actionResult.entrySet().removeIf(entry -> entry.getValue().size() == 0);
        return this.actionResult;
    };

}
