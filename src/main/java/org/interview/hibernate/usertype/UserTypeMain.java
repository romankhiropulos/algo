package org.interview.hibernate.usertype;

import org.hibernate.HibernateException;
import org.interview.InterviewMain;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class UserTypeMain {
    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(InterviewMain.class);
        ConfigurableApplicationContext context = null;
        try {
            context = application.run(args);
            System.out.println(context.toString() + " контекст запустился");
            UserTypeTestService bean = context.getBean(UserTypeTestService.class);
            bean.testUserType();
            System.out.println(context.toString() + " закрывается контекст");
            context.close();
        } catch (HibernateException exception) {
            throw exception;
        } catch (Exception exception) {
            throw exception;
        } finally {
            if (context != null) {
                context.close();
            }
        }
    }
}
