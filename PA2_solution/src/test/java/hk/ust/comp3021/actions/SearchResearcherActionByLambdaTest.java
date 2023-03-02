package hk.ust.comp3021.actions;

import hk.ust.comp3021.action.SearchResearcherAction;
import hk.ust.comp3021.action.SearchResearcherAction.SearchResearcherKind;
import hk.ust.comp3021.person.User;
import hk.ust.comp3021.resource.Paper;
import hk.ust.comp3021.utils.TestKind;
import hk.ust.comp3021.MiniMendeleyEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

public class SearchResearcherActionByLambdaTest {
    @Tag(TestKind.PUBLIC)
    @Test
    void testSearchResearcherAction_ActionsSize() {
        MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        int originalSize = engine.getActions().size();

        SearchResearcherAction action = new SearchResearcherAction("Action_1", user, new Date(), "3", "10", SearchResearcherKind.PAPER_WITHIN_YEAR);
        engine.processSearchResearcherActionByLambda(user, action);

        int currentSize = engine.getActions().size();
        assertEquals(currentSize, originalSize + 1);
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testSearchResearcherAction_SearchByPaperWithinYear() {
        MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        SearchResearcherAction action1 = new SearchResearcherAction("Action_1", user, new Date(), "3", "10", SearchResearcherKind.PAPER_WITHIN_YEAR);
        HashMap<String, List<Paper>> searchResult1 = engine.processSearchResearcherActionByLambda(user, action1);

        assertEquals(searchResult1.size(), 11);
        assertEquals(searchResult1.get("Xiaohong Li").get(0).getTitle(), "Proteus: Computing disjunctive loop summary via path dependency analysis");
        assertEquals(searchResult1.get("Alvin Cheung").size(), 5);
        assertEquals(searchResult1.get("Yang Liu").get(2).getTitle(), "Automatic Loop Summarization via Path Dependency Analysis");
        
        SearchResearcherAction action2 = new SearchResearcherAction("Action_2", user, new Date(), "4", "8", SearchResearcherKind.PAPER_WITHIN_YEAR);
        HashMap<String, List<Paper>> searchResult2 = engine.processSearchResearcherActionByLambda(user, action2);

        assertEquals(searchResult2.size(), 5);
        assertEquals(searchResult2.get("Alvin Cheung").size(), 5);
        assertEquals(searchResult2.get("Alvin Cheung").get(4).getTitle(), "Synthesizing highly expressive SQL queries from input-output examples");
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testSearchResearcherAction_SearchByJournalPublishTimes() {
    	MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        SearchResearcherAction action1 = new SearchResearcherAction("Action_1", user, new Date(), "ACM SIGPLAN Notices", "200", SearchResearcherKind.JOURNAL_PUBLISH_TIMES);
        HashMap<String, List<Paper>> searchResult1 = engine.processSearchResearcherActionByLambda(user, action1);

        assertEquals(searchResult1.size(), 28);
        assertEquals(searchResult1.get("Guilherme Ottoni").get(0).getTitle(), "The HipHop compiler for PHP");
        assertEquals(searchResult1.get("Bertrand Jeannet").get(0).getTitle(), "Abstract acceleration of general linear loops");
        assertEquals(searchResult1.get("Isil Dillig").size(), 2);
        
        SearchResearcherAction action2 = new SearchResearcherAction("Action_2", user, new Date(), "Lecture Notes in Computer Science (including subseries Lecture Notes in Artificial Intelligence and Lecture Notes in Bioinformatics)", "300", SearchResearcherKind.JOURNAL_PUBLISH_TIMES);
        HashMap<String, List<Paper>> searchResult2 = engine.processSearchResearcherActionByLambda(user, action2);

        assertEquals(searchResult2.size(), 51);
        assertEquals(searchResult2.get("Mooly Sagiv").size(), 3);
        assertEquals(searchResult2.get("Mooly Sagiv").get(2).getTitle(), "Statically inferring complex heap, array, and numeric invariants");
        assertEquals(searchResult2.get("Thomas Reps").size(), 4);
        assertEquals(searchResult2.get("Thomas Reps").get(0).getTitle(), "Revamping TVLA: Making parametric shape analysis competitive");
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testSearchResearcherAction_SearchByKeywordSimilarity() {
    	MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        SearchResearcherAction action1 = new SearchResearcherAction("Action_1", user, new Date(), "28", "M. Martel", SearchResearcherKind.KEYWORD_SIMILARITY);
        HashMap<String, List<Paper>> searchResult1 = engine.processSearchResearcherActionByLambda(user, action1);

        assertEquals(searchResult1.size(), 5);
        assertEquals(searchResult1.get("Wei Ngan Chin").get(0).getTitle(), "Loop invariant synthesis in a combined abstract domain");
        assertEquals(searchResult1.containsKey("Shengchao Qin"), true);
        assertEquals(searchResult1.containsKey("Xin Chen"), true);

        SearchResearcherAction action2 = new SearchResearcherAction("Action_2", user, new Date(), "50", "Vivien Maisonneuve", SearchResearcherKind.KEYWORD_SIMILARITY);
        HashMap<String, List<Paper>> searchResult2 = engine.processSearchResearcherActionByLambda(user, action2);

        assertEquals(searchResult2.size(), 0);

        SearchResearcherAction action3 = new SearchResearcherAction("Action_3", user, new Date(), "35", "Woosuk Lee", SearchResearcherKind.KEYWORD_SIMILARITY);
        HashMap<String, List<Paper>> searchResult3 = engine.processSearchResearcherActionByLambda(user, action3);

        assertEquals(searchResult3.size(), 3);
        assertEquals(searchResult3.get("Nadia Polikarpova").get(0).getTitle(), "Just-in-time learning for bottom-up enumerative synthesis");
        assertEquals(searchResult3.containsKey("Hila Peleg"), true);
        assertEquals(searchResult3.containsKey("Shraddha Barke"), true);
    }

    @Tag(TestKind.HIDDEN)
    @Test
    void testSearchResearcherAction_SearchByPaperWithinYear_Hidden1() {
        MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        SearchResearcherAction action1 = new SearchResearcherAction("Action_1", user, new Date(), "2", "2", SearchResearcherKind.PAPER_WITHIN_YEAR);
        HashMap<String, List<Paper>> searchResult1 = engine.processSearchResearcherActionByLambda(user, action1);
        
        assertEquals(searchResult1.size(), 1);
        assertEquals(searchResult1.get("Alvin Cheung").get(0).getTitle(), "Katara: synthesizing CRDTs with verified lifting");
        assertEquals(searchResult1.get("Alvin Cheung").size(), 3);
        assertEquals(searchResult1.get("Alvin Cheung").get(2).getJournal(), null);
        
        SearchResearcherAction action2 = new SearchResearcherAction("Action_2", user, new Date(), "2", "4", SearchResearcherKind.PAPER_WITHIN_YEAR);
        HashMap<String, List<Paper>> searchResult2 = engine.processSearchResearcherActionByLambda(user, action2);

        assertEquals(searchResult2.size(), 9);
        assertEquals(searchResult2.get("Martin Kellogg").size(), 2);
        assertEquals(searchResult2.get("Ziteng Wang").get(1).getJournal(), "Proceedings of the ACM on Programming Languages");
        assertEquals(searchResult2.get("Alvin Cheung").get(2).getJournal(), null);
        assertEquals(searchResult2.get("Manu Sridharan").get(0).getYear(), 2020);
    }

    @Tag(TestKind.HIDDEN)
    @Test
    void testSearchResearcherAction_SearchByPaperWithinYear_Hidden2() {
        MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        SearchResearcherAction action1 = new SearchResearcherAction("Action_1", user, new Date(), "5", "10", SearchResearcherKind.PAPER_WITHIN_YEAR);
        HashMap<String, List<Paper>> searchResult1 = engine.processSearchResearcherActionByLambda(user, action1);
        
        assertEquals(searchResult1.size(), 1);
        assertEquals(searchResult1.get("Alvin Cheung").get(0).getYear(), 2022);
        assertEquals(searchResult1.get("Alvin Cheung").get(4).getYear(), 2017);
        assertEquals(searchResult1.get("Alvin Cheung").size(), 5);
        assertEquals(searchResult1.get("Alvin Cheung").get(3).getTitle(), "Leveraging Application Data Constraints to OptimizeDatabase-Backed Web Applications");
        
    }

    @Tag(TestKind.HIDDEN)
    @Test
    void testSearchResearcherAction_SearchByJournalPublishTimes_Hidden1() {
    	MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        SearchResearcherAction action1 = new SearchResearcherAction("Action_1", user, new Date(), "Proceedings of the ACM on Programming Languages", "200", SearchResearcherKind.JOURNAL_PUBLISH_TIMES);
        HashMap<String, List<Paper>> searchResult1 = engine.processSearchResearcherActionByLambda(user, action1);

        assertEquals(searchResult1.size(), 29);
        assertEquals(searchResult1.get("Peter W. O'Hearn").get(0).getYear(), 2019);
        assertEquals(searchResult1.get("Shraddha Barke").get(0).getTitle(), "Just-in-time learning for bottom-up enumerative synthesis");
        assertEquals(searchResult1.get("Nadia Polikarpova").size(), 3);
        assertEquals(searchResult1.get("Ilya Sergey").get(0).getDoi(), "10.1145/3290370");
    }

    @Tag(TestKind.HIDDEN)
    @Test
    void testSearchResearcherAction_SearchByJournalPublishTimes_Hidden2() {
    	MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        SearchResearcherAction action1 = new SearchResearcherAction("Action_1", user, new Date(), "null", "100", SearchResearcherKind.JOURNAL_PUBLISH_TIMES);
        HashMap<String, List<Paper>> searchResult1 = engine.processSearchResearcherActionByLambda(user, action1);

        assertEquals(searchResult1.size(), 0);
    }

    @Tag(TestKind.HIDDEN)
    @Test
    void testSearchResearcherAction_SearchByKeywordSimilarity_Hidden1() {
    	MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        SearchResearcherAction action1 = new SearchResearcherAction("Action_1", user, new Date(), "50", "Xiangyu Zhou", SearchResearcherKind.KEYWORD_SIMILARITY);
        HashMap<String, List<Paper>> searchResult1 = engine.processSearchResearcherActionByLambda(user, action1);

        assertEquals(searchResult1.size(), 0);

        SearchResearcherAction action2 = new SearchResearcherAction("Action_2", user, new Date(), "0", "Xiangyu Zhou", SearchResearcherKind.KEYWORD_SIMILARITY);
        HashMap<String, List<Paper>> searchResult2 = engine.processSearchResearcherActionByLambda(user, action2);

        assertEquals(searchResult2.size(), 175);
    }

    @Tag(TestKind.HIDDEN)
    @Test
    void testSearchResearcherAction_SearchByKeywordSimilarity_Hidden2() {
    	MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        SearchResearcherAction action1 = new SearchResearcherAction("Action_1", user, new Date(), "100", "Chenglong Wang", SearchResearcherKind.KEYWORD_SIMILARITY);
        HashMap<String, List<Paper>> searchResult1 = engine.processSearchResearcherActionByLambda(user, action1);

        assertEquals(searchResult1.size(), 0);

        SearchResearcherAction action2 = new SearchResearcherAction("Action_2", user, new Date(), "30", "Chenglong Wang", SearchResearcherKind.KEYWORD_SIMILARITY);
        HashMap<String, List<Paper>> searchResult2 = engine.processSearchResearcherActionByLambda(user, action2);

        assertEquals(searchResult2.size(), 4);
        assertEquals(searchResult2.get("Ruzica Piskac").get(0).getJournal(), null);
        assertEquals(searchResult2.get("Mark Santolucito").get(0).getTitle(), "Can Reactive Synthesis and Syntax-Guided Synthesis Be Friends?");
        assertEquals(searchResult2.get("Wonhyuk Choi").get(0).getYear(), 2022);
    }
}
