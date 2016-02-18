package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Map<String,String[]> mapParam = request.getParameterMap();
        if (mapParam.containsKey("act")) {
            int id = Integer.parseInt(mapParam.get("id")[0]);
            if (mapParam.get("act")[0].equals("delete")) {
                UserMealsUtil.mealMap.remove(id);
            } else if (mapParam.get("act")[0].equals("edit")) {
                request.setAttribute("editMeal", UserMealsUtil.mealMap.get(id));
            }
        } else {
            request.setAttribute("editMeal", null);//new UserMeal(null,"",0));
        }

        LOG.debug("redirect to mealList");

        List<UserMeal> userMealList = new ArrayList<>(UserMealsUtil.mealMap.values());
        List<UserMealWithExceed> mealWithExceedList = UserMealsUtil.getFilteredMealsWithExceeded(userMealList,
                LocalTime.of(10, 0),  LocalTime.of(20, 0), 2000);
        request.setAttribute("meals",mealWithExceedList);

        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        //response.sendRedirect("mealList.jsp");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String,String[]> mapParam = request.getParameterMap();

        if (!mapParam.get("idd")[0].isEmpty()) {
            int id = Integer.parseInt(mapParam.get("idd")[0]);
            UserMealsUtil.mealMap.remove(id);
        }

        UserMeal newMeal;
        newMeal = new UserMeal( LocalDateTime.parse(mapParam.get("dateTime")[0]), mapParam.get("description")[0], Integer.parseInt(mapParam.get("calories")[0]));

        UserMealsUtil.mealMap.put(newMeal.getId(),newMeal);

        doGet(request, response);
    }
}