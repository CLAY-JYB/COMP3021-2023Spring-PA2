package hk.ust.comp3021.actions;

import hk.ust.comp3021.action.StatisticalInformationAction;
import hk.ust.comp3021.action.StatisticalInformationAction.InfoKind;
import hk.ust.comp3021.person.User;
import hk.ust.comp3021.utils.TestKind;
import hk.ust.comp3021.MiniMendeleyEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ProfileStatisticalInformationActionByLambdaTest {
    @Tag(TestKind.PUBLIC)
    @Test
    void testProfileStatisticalInformationAction_ActionsSize() {
        MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        int originalSize = engine.getActions().size();

        StatisticalInformationAction action = new StatisticalInformationAction("Action_1", user, new Date(), InfoKind.AVERAGE);
        engine.processStatisticalInformationActionByLambda(user, action);

        int currentSize = engine.getActions().size();
        assertEquals(currentSize, originalSize + 1);
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testProfileStatisticalInformationAction_ProfileAverage() {
        MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        StatisticalInformationAction action = new StatisticalInformationAction("Action_1", user, new Date(), InfoKind.AVERAGE);
        Map<String, Double> searchResult1 = engine.processStatisticalInformationActionByLambda(user, action);

        assertEquals(searchResult1.size(), 303);
        assertEquals(searchResult1.get("Guilherme Ottoni"), 1.0);
        assertEquals(searchResult1.get("Eric Goubault"), 2.0);
        assertEquals(searchResult1.get("Alvin Cheung"), 1.6666666666666667);
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testProfileStatisticalInformationAction_ProfileMaximal() {
    	MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        StatisticalInformationAction action = new StatisticalInformationAction("Action_1", user, new Date(), InfoKind.MAXIMAL);
        Map<String, Double> searchResult1 = engine.processStatisticalInformationActionByLambda(user, action);

        assertEquals(searchResult1.size(), 16);
        assertEquals(searchResult1.get("ACM Transactions on Programming Languages and Systems"), 1.0);
        assertEquals(searchResult1.get("Conference Record of the Annual ACM Symposium on Principles of Programming Languages"), 2.0);
        assertEquals(searchResult1.get("Proceedings of the ACM on Programming Languages"), 5.0);
    }

    @Tag(TestKind.HIDDEN)
    @Test
    void testProfileStatisticalInformationAction_ProfileAverage_Hidden() {
        MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        StatisticalInformationAction action = new StatisticalInformationAction("Action_1", user, new Date(), InfoKind.AVERAGE);
        Map<String, Double> searchResult1 = engine.processStatisticalInformationActionByLambda(user, action);

        assertEquals(searchResult1.size(), 303);
        assertEquals(searchResult1.get("Zheng Guo"), 1.5);
        assertEquals(searchResult1.get("George Balatsouras"), 1.0);
        assertEquals(searchResult1.get("Isil Dillig"), 1.0);
        assertEquals(searchResult1.get("Eric Goubault"), 2.0);
        assertEquals(searchResult1.get("Nadia Polikarpova"), 2.0);
        assertEquals(searchResult1.get("Mooly Sagiv"), 1.3333333333333333);
        assertEquals(searchResult1.get("Josh Berdine"), 1.5);
    }
    
    @Tag(TestKind.HIDDEN)
    @Test
    void testProfileStatisticalInformationAction_ProfileMaximal_Hidden() {
    	MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        StatisticalInformationAction action = new StatisticalInformationAction("Action_1", user, new Date(), InfoKind.MAXIMAL);
        Map<String, Double> searchResult1 = engine.processStatisticalInformationActionByLambda(user, action);

        assertEquals(searchResult1.size(), 16);
        assertEquals(searchResult1.get("Science of Computer Programming"), 1.0);
        assertEquals(searchResult1.get("Empirical Software Engineering"), 1.0);
        assertEquals(searchResult1.get("Lecture Notes in Computer Science (including subseries Lecture Notes in Artificial Intelligence and Lecture Notes in Bioinformatics)"), 2.0);
        assertEquals(searchResult1.get("Electronic Notes in Theoretical Computer Science"), 2.0);
        assertEquals(searchResult1.entrySet().stream().filter(entry -> entry.getValue() == 1.0).count(), 11);
    }
}
