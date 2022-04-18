package server.api;

import commons.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.sevice.QuestionService;

@RestController
@RequestMapping("api/question")
public class QuestionController {

    private final QuestionService questionService;


    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * This method will retrieve a random question based on the provided type of Question
     * @param type the type of question to retrieve
     * @return the question
     */
    public Question getRandomQuestion(String type) {
        switch (type) {
            case "guess":
                return questionService.getRandomGuessQuestion();
            case "multipleChoice":
                return questionService.getRandomMultipleChoiceQuestion();
            case "insteadOf":
                return questionService.getRandomInsteadOfQuestion();
            default:
                return questionService.getRandomMostEnergyQuestion();
        }
    }


}
