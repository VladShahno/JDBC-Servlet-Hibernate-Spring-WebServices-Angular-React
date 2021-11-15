package com.nixsolutions.crudapp.util;

import com.nixsolutions.crudapp.entity.User;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class UserListTag extends SimpleTagSupport {

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public long getAge(Date birthday) {
        return ChronoUnit.YEARS.between(birthday.toLocalDate(),
                LocalDate.now());
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        if (users.size() > 0) {
            StringBuilder output = new StringBuilder();
            output.append("<table class=\"table table-bordered\" id=\"users\">"
                    + "<thead><tr><th>login</th>" + "<th>First Name</th>"
                    + "<th>Last Name</th>" + "<th>Age</th>"
                    + "<th>Role Name</th>" + "<th>Actions</th></tr></thead>");
            for (User user : users) {
                String delete =
                        "<a href=\"users/delete?login=" + user.getLogin()
                                + "\" onclick=\"return confirm"
                                + "('Are you sure?')\">delete</a><span>,</span>";
                output.append(
                        "<tbody><tr><td>" + user.getLogin() + "</td>" + "<td>"
                                + user.getFirstName() + "</td>" + "<td>"
                                + user.getLastName() + "</td>" + "<td>"
                                + getAge(user.getBirthday()) + "</td>" + "<td>"
                                + user.getRole().getName() + "</td><td>");
                if (!user.getRole().getName().equals("ADMIN")) {
                    output.append(delete);
                }
                output.append("<a href= \"users/update?login=" + user.getLogin()
                        + "\"> update</a></td></tr></tbody>");
            }
            output.append("</table>");
            out.println(output.toString());
        }
    }
}