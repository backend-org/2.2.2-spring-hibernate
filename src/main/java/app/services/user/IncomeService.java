package app.services.user;

import app.config.LoanProperties;
import app.model.UserIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    private LoanProperties loanProperties;
    @Autowired
    private RestTemplate restTemplate;

    public int getUserIncome(int userId) {
        List<UserIncome> usersIncomeList = restTemplate.exchange(
                loanProperties.getUsersIncomeUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserIncome>>() {
                }
        ).getBody();
        int income = Optional.ofNullable(usersIncomeList)
                .stream()
                .flatMap(List::stream)
                .filter(user -> user.getId() == userId)
                .map(UserIncome::getIncome)
                .findFirst()
                .orElse(0);
        return income;
    }
}
