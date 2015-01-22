package com.livejournal.uisteps.thucydides;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

/**
 *
 * @author m.prytkova
 */
public class Databases {

    Connection c = null;//Соединение с БД
    List<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();

    public BaseConnect workWithDB() {
        answers.clear();
        return new BaseConnect();
    }

    public class BaseConnect {

        public BaseSelect conect() {

            String driver = "com.mysql.jdbc.Driver";//Имя драйвера

            try {
                Class.forName(driver);//Регистрируем драйвер
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
                Assert.assertTrue("No connection", false);
            }
            return new BaseSelect();
        }

    }

    public class BaseSelect {

        public BaseSelect select(String select, String column) {

            String user = "root";//Логин пользователя
            String password = "";//Пароль пользователя
            String url = "jdbc:mysql://127.0.0.1:2222/livejournal";//URL адрес
            ArrayList<String> answer = new ArrayList<>();

            try {
                c = (Connection) DriverManager.getConnection(url, user, password);//Установка соединения с БД
                ThucydidesUtils.putToSession("connect", c);
                Statement st = c.createStatement();//Готовим запрос
                ResultSet rs = st.executeQuery(select);//Выполняем запрос к БД, результат в переменной rs
                while (rs.next()) {
                    answer.add(rs.getString(column));
                    // System.out.println(rs.getString(column));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Assert.assertTrue("No connection", false);
            }
            answers.add(answer);
            return new BaseSelect();
        }

        public List<ArrayList<String>> finish() {
            //Обязательно необходимо закрыть соединение
            try {
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Assert.assertTrue("No connection", false);
            }
            return answers;
        }
    }

}