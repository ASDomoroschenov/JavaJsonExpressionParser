package ru.mai.parser.impl;

import ru.mai.parser.OperationExecutor;

import java.util.List;
import java.util.Optional;

public class OperationExecutorImpl implements OperationExecutor {

    @Override
    public Integer sum(List<Integer> values) {
        Optional<Integer> result = values.stream().reduce(Integer::sum);
        return result.orElse(0);
    }

    @Override
    public Integer subtraction(List<Integer> values) {
        Optional<Integer> result = values.stream().reduce((first, second) -> first - second);
        return result.orElse(0);
    }

}
