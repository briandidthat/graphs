package com.serpiente.graphs.algorithms;

import com.serpiente.graphs.model.AdjacencyMatrixGraph;
import com.serpiente.graphs.model.Graph;
import com.serpiente.graphs.utils.GraphHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In this question, you are to design a course schedule considering all of the prerequisite for each course. You will
 * have two lists: One for all of the courses, One for all of the prerequisite for each course.
 */

public class PreRequisiteCourse {
    private final GraphHelper graphHelper = new GraphHelper();

    public List<String> order(List<String> courses, Map<String, List<String>> prereqs) {
        Graph courseGraph = new AdjacencyMatrixGraph(courses.size(), true, Graph.GraphType.DIRECTED);
        // Set up mapping from the course name to unique id and do the opposite as well.
        Map<String, Integer> courseMap = new HashMap<>();
        Map<Integer, String> idCourseMap = new HashMap<>();
        // add all of the courses to both hash-maps.
        for (int i = 0; i < courses.size(); i++) {
            courseMap.put(courses.get(i), i);
            idCourseMap.put(i, courses.get(i));
        }
        // Add a graph edge for ever pre-req to the course.
        for (Map.Entry<String, List<String>> prereq : prereqs.entrySet()) {
            for (String course : prereq.getValue()) {
                courseGraph.addEdge(courseMap.get(prereq.getKey()),courseMap.get(course));
            }
        }
        // Call topological sort on the graph.
        List<Integer> courseIdList = graphHelper.topologicalSort(courseGraph);

        List<String> courseScheduleList = new ArrayList<>();

        for (int courseId : courseIdList) {
            courseScheduleList.add(idCourseMap.get(courseId));
        }

        return courseScheduleList;
    }
}
