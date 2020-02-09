package com.scu.niagaramodule.pojo;

import java.util.ArrayList;
import java.util.List;

public class SupervisorTemperatureExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SupervisorTemperatureExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTimestampIsNull() {
            addCriterion("TIMESTAMP is null");
            return (Criteria) this;
        }

        public Criteria andTimestampIsNotNull() {
            addCriterion("TIMESTAMP is not null");
            return (Criteria) this;
        }

        public Criteria andTimestampEqualTo(Long value) {
            addCriterion("TIMESTAMP =", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampNotEqualTo(Long value) {
            addCriterion("TIMESTAMP <>", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampGreaterThan(Long value) {
            addCriterion("TIMESTAMP >", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampGreaterThanOrEqualTo(Long value) {
            addCriterion("TIMESTAMP >=", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampLessThan(Long value) {
            addCriterion("TIMESTAMP <", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampLessThanOrEqualTo(Long value) {
            addCriterion("TIMESTAMP <=", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampIn(List<Long> values) {
            addCriterion("TIMESTAMP in", values, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampNotIn(List<Long> values) {
            addCriterion("TIMESTAMP not in", values, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampBetween(Long value1, Long value2) {
            addCriterion("TIMESTAMP between", value1, value2, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampNotBetween(Long value1, Long value2) {
            addCriterion("TIMESTAMP not between", value1, value2, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTrendflagsIsNull() {
            addCriterion("TRENDFLAGS is null");
            return (Criteria) this;
        }

        public Criteria andTrendflagsIsNotNull() {
            addCriterion("TRENDFLAGS is not null");
            return (Criteria) this;
        }

        public Criteria andTrendflagsEqualTo(Integer value) {
            addCriterion("TRENDFLAGS =", value, "trendflags");
            return (Criteria) this;
        }

        public Criteria andTrendflagsNotEqualTo(Integer value) {
            addCriterion("TRENDFLAGS <>", value, "trendflags");
            return (Criteria) this;
        }

        public Criteria andTrendflagsGreaterThan(Integer value) {
            addCriterion("TRENDFLAGS >", value, "trendflags");
            return (Criteria) this;
        }

        public Criteria andTrendflagsGreaterThanOrEqualTo(Integer value) {
            addCriterion("TRENDFLAGS >=", value, "trendflags");
            return (Criteria) this;
        }

        public Criteria andTrendflagsLessThan(Integer value) {
            addCriterion("TRENDFLAGS <", value, "trendflags");
            return (Criteria) this;
        }

        public Criteria andTrendflagsLessThanOrEqualTo(Integer value) {
            addCriterion("TRENDFLAGS <=", value, "trendflags");
            return (Criteria) this;
        }

        public Criteria andTrendflagsIn(List<Integer> values) {
            addCriterion("TRENDFLAGS in", values, "trendflags");
            return (Criteria) this;
        }

        public Criteria andTrendflagsNotIn(List<Integer> values) {
            addCriterion("TRENDFLAGS not in", values, "trendflags");
            return (Criteria) this;
        }

        public Criteria andTrendflagsBetween(Integer value1, Integer value2) {
            addCriterion("TRENDFLAGS between", value1, value2, "trendflags");
            return (Criteria) this;
        }

        public Criteria andTrendflagsNotBetween(Integer value1, Integer value2) {
            addCriterion("TRENDFLAGS not between", value1, value2, "trendflags");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andValueIsNull() {
            addCriterion("VALUE is null");
            return (Criteria) this;
        }

        public Criteria andValueIsNotNull() {
            addCriterion("VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andValueEqualTo(Double value) {
            addCriterion("VALUE =", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotEqualTo(Double value) {
            addCriterion("VALUE <>", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueGreaterThan(Double value) {
            addCriterion("VALUE >", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueGreaterThanOrEqualTo(Double value) {
            addCriterion("VALUE >=", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLessThan(Double value) {
            addCriterion("VALUE <", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLessThanOrEqualTo(Double value) {
            addCriterion("VALUE <=", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueIn(List<Double> values) {
            addCriterion("VALUE in", values, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotIn(List<Double> values) {
            addCriterion("VALUE not in", values, "value");
            return (Criteria) this;
        }

        public Criteria andValueBetween(Double value1, Double value2) {
            addCriterion("VALUE between", value1, value2, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotBetween(Double value1, Double value2) {
            addCriterion("VALUE not between", value1, value2, "value");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagIsNull() {
            addCriterion("TRENDFLAGS_TAG is null");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagIsNotNull() {
            addCriterion("TRENDFLAGS_TAG is not null");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagEqualTo(String value) {
            addCriterion("TRENDFLAGS_TAG =", value, "trendflagsTag");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagNotEqualTo(String value) {
            addCriterion("TRENDFLAGS_TAG <>", value, "trendflagsTag");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagGreaterThan(String value) {
            addCriterion("TRENDFLAGS_TAG >", value, "trendflagsTag");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagGreaterThanOrEqualTo(String value) {
            addCriterion("TRENDFLAGS_TAG >=", value, "trendflagsTag");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagLessThan(String value) {
            addCriterion("TRENDFLAGS_TAG <", value, "trendflagsTag");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagLessThanOrEqualTo(String value) {
            addCriterion("TRENDFLAGS_TAG <=", value, "trendflagsTag");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagLike(String value) {
            addCriterion("TRENDFLAGS_TAG like", value, "trendflagsTag");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagNotLike(String value) {
            addCriterion("TRENDFLAGS_TAG not like", value, "trendflagsTag");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagIn(List<String> values) {
            addCriterion("TRENDFLAGS_TAG in", values, "trendflagsTag");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagNotIn(List<String> values) {
            addCriterion("TRENDFLAGS_TAG not in", values, "trendflagsTag");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagBetween(String value1, String value2) {
            addCriterion("TRENDFLAGS_TAG between", value1, value2, "trendflagsTag");
            return (Criteria) this;
        }

        public Criteria andTrendflagsTagNotBetween(String value1, String value2) {
            addCriterion("TRENDFLAGS_TAG not between", value1, value2, "trendflagsTag");
            return (Criteria) this;
        }

        public Criteria andStatusTagIsNull() {
            addCriterion("STATUS_TAG is null");
            return (Criteria) this;
        }

        public Criteria andStatusTagIsNotNull() {
            addCriterion("STATUS_TAG is not null");
            return (Criteria) this;
        }

        public Criteria andStatusTagEqualTo(String value) {
            addCriterion("STATUS_TAG =", value, "statusTag");
            return (Criteria) this;
        }

        public Criteria andStatusTagNotEqualTo(String value) {
            addCriterion("STATUS_TAG <>", value, "statusTag");
            return (Criteria) this;
        }

        public Criteria andStatusTagGreaterThan(String value) {
            addCriterion("STATUS_TAG >", value, "statusTag");
            return (Criteria) this;
        }

        public Criteria andStatusTagGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS_TAG >=", value, "statusTag");
            return (Criteria) this;
        }

        public Criteria andStatusTagLessThan(String value) {
            addCriterion("STATUS_TAG <", value, "statusTag");
            return (Criteria) this;
        }

        public Criteria andStatusTagLessThanOrEqualTo(String value) {
            addCriterion("STATUS_TAG <=", value, "statusTag");
            return (Criteria) this;
        }

        public Criteria andStatusTagLike(String value) {
            addCriterion("STATUS_TAG like", value, "statusTag");
            return (Criteria) this;
        }

        public Criteria andStatusTagNotLike(String value) {
            addCriterion("STATUS_TAG not like", value, "statusTag");
            return (Criteria) this;
        }

        public Criteria andStatusTagIn(List<String> values) {
            addCriterion("STATUS_TAG in", values, "statusTag");
            return (Criteria) this;
        }

        public Criteria andStatusTagNotIn(List<String> values) {
            addCriterion("STATUS_TAG not in", values, "statusTag");
            return (Criteria) this;
        }

        public Criteria andStatusTagBetween(String value1, String value2) {
            addCriterion("STATUS_TAG between", value1, value2, "statusTag");
            return (Criteria) this;
        }

        public Criteria andStatusTagNotBetween(String value1, String value2) {
            addCriterion("STATUS_TAG not between", value1, value2, "statusTag");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}