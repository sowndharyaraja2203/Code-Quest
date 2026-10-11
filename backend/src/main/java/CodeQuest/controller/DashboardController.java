package CodeQuest.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CodeQuest.service.DashboardService;
import CodeQuest.service.DashboardService.DashboardStats;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "https://code-quest-virid-five.vercel.app",
    "https://code-quest-sound17.vercel.app",
    "https://code-quest-4pwx70dza-sound17.vercel.app"
})
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping("/{email}")
    public DashboardStats getDashboard(
            @PathVariable String email) {

        return dashboardService.getDashboardStats(email);
    }
}