package org.interview.sql.isolation;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.interview.InterviewMain;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
public class IsolationMain {

    public static void main(String[] args) {

        final SpringApplication application = new SpringApplication(InterviewMain.class);
        ConfigurableApplicationContext context = null;
        try {
            context = application.run(args);
            log.info(context.toString() + " контекст запустился");
            IsolationTesting bean = context.getBean(IsolationTesting.class);
            bean.testingLostUpdate();
            log.info(context.toString() + " закрывается контекст");
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
