import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApplication {
    // Quiz Data
    String[][] questions = {
        {"What is the capital of France?", "Berlin", "Madrid", "Paris", "Rome", "3"},
        {"Which programming language is known as 'snake case'?", "Java", "Python", "C++", "JavaScript", "2"},
        {"What is 5 + 3?", "6", "8", "7", "9", "2"}
    };

    int currentQuestion = 0;
    int score = 0;
    int timer = 10; // Time limit for each question
    Timer countdownTimer;

    JFrame frame;
    JLabel questionLabel, timerLabel;
    JRadioButton[] options = new JRadioButton[4];
    ButtonGroup group = new ButtonGroup();
    JButton submitButton, nextButton;

    public QuizApplication() {
        // Initialize the frame
        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new GridLayout(7, 1));

        // Timer Label
        timerLabel = new JLabel("Time Left: " + timer + " seconds", JLabel.CENTER);
        frame.add(timerLabel);

        // Question Label
        questionLabel = new JLabel("", JLabel.CENTER);
        frame.add(questionLabel);

        // Options
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            frame.add(options[i]);
        }

        // Submit Button
        submitButton = new JButton("Submit");
        frame.add(submitButton);

        // Next Button
        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        frame.add(nextButton);

        // Load first question
        loadQuestion();

        // Submit Button Action
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitAnswer();
            }
        });

        // Next Button Action
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextQuestion();
            }
        });

        frame.setVisible(true);
    }

    // Load a question and reset the timer
    private void loadQuestion() {
        if (currentQuestion < questions.length) {
            questionLabel.setText(questions[currentQuestion][0]);
            for (int i = 0; i < 4; i++) {
                options[i].setText(questions[currentQuestion][i + 1]);
                options[i].setSelected(false);
            }
            submitButton.setEnabled(true);
            nextButton.setEnabled(false);
            timer = 10;
            startTimer();
        } else {
            showResults();
        }
    }

    // Start a countdown timer
    private void startTimer() {
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer--;
                timerLabel.setText("Time Left: " + timer + " seconds");
                if (timer <= 0) {
                    countdownTimer.stop();
                    submitAnswer(); // Auto-submit when time is up
                }
            }
        });
        countdownTimer.start();
    }

    // Handle answer submission
    private void submitAnswer() {
        countdownTimer.stop();
        submitButton.setEnabled(false);
        int correctOption = Integer.parseInt(questions[currentQuestion][5]);
        int selectedOption = -1;

        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selectedOption = i + 1;
                break;
            }
        }

        if (selectedOption == correctOption) {
            score++;
        }

        nextButton.setEnabled(true);
    }

    // Load the next question
    private void nextQuestion() {
        currentQuestion++;
        loadQuestion();
    }

    // Show final results
    private void showResults() {
        JOptionPane.showMessageDialog(frame, "Quiz Over! Your score: " + score + "/" + questions.length);
        frame.dispose();
    }

    public static void main(String[] args) {
        new QuizApplication();
    }
}