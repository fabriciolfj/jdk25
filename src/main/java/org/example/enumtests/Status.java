package org.example.enumtests;

import java.util.Set;

public enum Status {

    ACTIVE {
        @Override
        public Set<Status> getOtherStatus() {
            return Set.of(INATIVE, ERROR);
        }
    },
    INATIVE {
        @Override
        public Set<Status> getOtherStatus() {
            return Set.of(ACTIVE);
        }
    },
    ERROR {
        @Override
        public Set<Status> getOtherStatus() {
            return Set.of(ACTIVE, INATIVE);
        }
    };

    abstract Set<Status> getOtherStatus();

    public boolean isAllowedChange(final Status taget) {
        return getOtherStatus().contains(taget);
    }
}
