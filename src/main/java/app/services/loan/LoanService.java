package app.services.loan;

import app.config.LoanProperties;
import app.exceptions.UserNotFoundException;
import app.model.Car;
import app.model.User;
import app.services.user.IncomeService;
import app.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoanService {

    @Autowired
    private LoanProperties loanProperties;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private UserService userService;

    public Double calculateUserMaxLoan(int userId) {
        User user = userService.getUserById(userId);
        int userIncome = incomeService.getUserIncome(userId);
        int usersCarPrice = Optional.ofNullable(user)
                .map(User::getCar)
                .map(Car::getPrice)
                .orElse(0);
        if (user == null && userIncome == 0) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }

        if (userIncome <= loanProperties.getMinUserIncomeForLoanApprove() &&
                usersCarPrice <= loanProperties.getMinCarCostForLoanApprove()) {
            return 0.0;
        }
        return Math.max(userIncome * loanProperties.getMaxApproveMultiplierOfCountOfMonthsIncome(),
                usersCarPrice * loanProperties.getMaxApproveMultiplierOfCarCost());
    }
}
