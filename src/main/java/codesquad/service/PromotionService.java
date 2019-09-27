package codesquad.service;

import codesquad.domain.Promotion;
import codesquad.domain.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static codesquad.domain.PromotionType.*;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;

    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

    public List<Promotion> findSubDishList() {
        return findAll().stream().filter(dish -> dish.isType(SUB_DISH)).collect(Collectors.toList());
    }

    public List<Promotion> findMainDishList() {
        return findAll().stream().filter(dish -> dish.isType(MAIN_DISH)).collect(Collectors.toList());
    }

    public List<Promotion> findSoupAndStewList() {
        return findAll().stream().filter(dish -> dish.isType(SOUP_AND_STEW)).collect(Collectors.toList());
    }
}
