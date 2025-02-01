package com.mthien.yumble.service;

import com.mthien.yumble.entity.Ingredient;
import com.mthien.yumble.exception.AppException;
import com.mthien.yumble.exception.ErrorCode;
import com.mthien.yumble.mapper.IngredientMapper;
import com.mthien.yumble.payload.request.ingredient.CreateIngredientRequest;
import com.mthien.yumble.payload.request.ingredient.UpdateIngredientRequest;
import com.mthien.yumble.payload.response.ingredient.IngredientResponse;
import com.mthien.yumble.repository.IngredientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepo ingredientRepo;
    private final IngredientMapper ingredientMapper;

    public IngredientResponse create(CreateIngredientRequest request) {
        Ingredient ingredient = ingredientMapper.createIngredient(request);
        return ingredientMapper.toIngredientResponse(ingredientRepo.save(ingredient));
    }

    public IngredientResponse update(String ingredientId, UpdateIngredientRequest request) {
        Ingredient ingredient = ingredientRepo.findById(ingredientId)
                .orElseThrow(() -> new AppException(ErrorCode.INGREDIENT_NOT_FOUND));
        ingredientMapper.updateIngredient(ingredient, request);
        return ingredientMapper.toIngredientResponse(ingredientRepo.save(ingredient));
    }

    public IngredientResponse viewOne(String ingredientId) {
        Ingredient ingredient = ingredientRepo.findById(ingredientId)
                .orElseThrow(() -> new AppException(ErrorCode.INGREDIENT_NOT_FOUND));
        return ingredientMapper.toIngredientResponse(ingredient);
    }

    public List<IngredientResponse> viewAll() {
        List<Ingredient> ingredientList = ingredientRepo.findAll();
        return ingredientMapper.toListIngredientResponses(ingredientList);
    }

    public void delete(String ingredientId) {
        Ingredient ingredient = ingredientRepo.findById(ingredientId)
                .orElseThrow(() -> new AppException(ErrorCode.INGREDIENT_NOT_FOUND));
        ingredientRepo.delete(ingredient);
    }
}
