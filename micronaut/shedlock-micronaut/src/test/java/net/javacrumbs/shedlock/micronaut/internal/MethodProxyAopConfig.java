/**
 * Copyright 2009-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.javacrumbs.shedlock.micronaut.internal;


import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.micronaut.SchedulerLock;

import javax.inject.Singleton;
import java.io.IOException;

import static org.mockito.Mockito.mock;

public class MethodProxyAopConfig {

    @Singleton
    public LockProvider lockProvider() {
        return mock(LockProvider.class);
    }


    @Singleton
    public TestBean testBean() {
        return new TestBean();
    }

    @Singleton
    public AnotherTestBean anotherTestBean() {
        return new AnotherTestBeanImpl();
    }

    static class TestBean {

        public void noAnnotation() {
        }

        @SchedulerLock(name = "normal")
        public void normal() {
        }

        @SchedulerLock(name = "runtimeException", lockAtMostFor = 100)
        public Void throwsRuntimeException() {
            throw new RuntimeException();
        }

        @SchedulerLock(name = "exception")
        public void throwsException() throws Exception {
            throw new IOException();
        }

        @SchedulerLock(name = "returnsValue")
        public int returnsValue() {
            return 0;
        }

        @SchedulerLock(name = "${property.value}", lockAtLeastForString = "${property.lockAtMostFor}")
        public void spel() {

        }
    }

    interface AnotherTestBean {
        void runManually();
    }

    static class AnotherTestBeanImpl implements AnotherTestBean {

        @Override
        @SchedulerLock(name = "classAnnotation")
        public void runManually() {

        }
    }
}