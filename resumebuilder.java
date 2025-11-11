import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;


/**
 * Professional Java Swing Resume Builder (Enhanced Design).
 * Includes strict validation: All primary fields (including a new PROJECTS field) must be filled to generate/save.
 */
public class resumebuilder extends JFrame {

    // --- Components ---
    private JTextField nameField, emailField, phoneField;
    private JTextArea skillsArea, eduArea, expArea, projectsArea; // NEW: projectsArea
    private JEditorPane previewArea; // For HTML rendering
    private JButton generateBtn, saveBtn, resetBtn;

    public resumebuilder() {
        // --- Frame Setup ---
        setTitle("Professional Resume Builder (Mandatory Fields)");
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- Initialize, Layout, and Attach Components ---
        initComponents();
        layoutComponents();
        attachListeners();

        // Load the empty template view on startup
        generateResumeHTML(); 
    }

    private void initComponents() {
        // INPUT FIELDS (Blank)
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);

        // TEXT AREAS (Blank)
        skillsArea = new JTextArea(5, 20);
        eduArea = new JTextArea(5, 20);
        expArea = new JTextArea(5, 20);
        projectsArea = new JTextArea(5, 20); // INITIALIZE NEW FIELD
        
        skillsArea.setLineWrap(true); skillsArea.setWrapStyleWord(true);
        eduArea.setLineWrap(true); eduArea.setWrapStyleWord(true);
        expArea.setLineWrap(true); expArea.setWrapStyleWord(true);
        projectsArea.setLineWrap(true); projectsArea.setWrapStyleWord(true); // Set wrap for projects

        // PREVIEW AREA - JEditorPane for HTML rendering
        previewArea = new JEditorPane();
        previewArea.setEditable(false);
        previewArea.setContentType("text/html"); 

        // BUTTONS
        generateBtn = new JButton("Generate Professional Design");
        saveBtn = new JButton("Save as HTML (for PDF print)"); 
        resetBtn = new JButton("Reset Fields");
    }

    private void layoutComponents() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Details (All fields are mandatory)"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;

        // Rows 0-2 (Name, Email, Phone) - Single Line Fields
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Name: *"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; inputPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0; inputPanel.add(new JLabel("Email: *"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; inputPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0; inputPanel.add(new JLabel("Phone: *"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; inputPanel.add(phoneField, gbc);

        // Row 3: Skills (Multi-line)
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.NORTHEAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0; inputPanel.add(new JLabel("Skills: *"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0; inputPanel.add(new JScrollPane(skillsArea), gbc);

        // Row 4: Education (Multi-line)
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.NORTHEAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0; gbc.weighty = 0.0; inputPanel.add(new JLabel("Education: *"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0; inputPanel.add(new JScrollPane(eduArea), gbc);

        // Row 5: Experience (Multi-line)
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.NORTHEAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0; gbc.weighty = 0.0; inputPanel.add(new JLabel("Experience: *"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0; inputPanel.add(new JScrollPane(expArea), gbc);

        // Row 6: Projects (Multi-line) - NEW FIELD ADDED
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.NORTHEAST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0; gbc.weighty = 0.0; inputPanel.add(new JLabel("Projects: *"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0; inputPanel.add(new JScrollPane(projectsArea), gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(generateBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(resetBtn);

        add(inputPanel, BorderLayout.WEST);
        add(new JScrollPane(previewArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void attachListeners() {
        generateBtn.addActionListener(e -> generateOrValidateResume());
        saveBtn.addActionListener(e -> saveOrValidateResume());
        resetBtn.addActionListener(e -> resetFields());
    }
    
    // --- UPDATED VALIDATION LOGIC ---
    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty() || 
            emailField.getText().trim().isEmpty() || 
            phoneField.getText().trim().isEmpty() ||
            skillsArea.getText().trim().isEmpty() ||
            eduArea.getText().trim().isEmpty() ||
            expArea.getText().trim().isEmpty() ||
            projectsArea.getText().trim().isEmpty()) { // CHECK NEW PROJECTS FIELD
            
            JOptionPane.showMessageDialog(this, 
                "All fields marked with '*' (Name, Email, Phone, Skills, Education, Experience, Projects) must be filled to generate a professional resume.", 
                "Missing Required Fields", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void generateOrValidateResume() {
        if (validateFields()) {
            generateResumeHTML();
        }
    }
    
    private void saveOrValidateResume() {
        if (validateFields()) {
            saveResumeHTML();
        }
    }

    // Helper function to convert TextArea lines into HTML list items
    private String convertToHtmlList(String text, String listStyle) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }
        String[] lines = text.split("\\r?\\n");
        StringBuilder sb = new StringBuilder();
        
        sb.append("<ul style='list-style-type: ").append(listStyle).append("; padding-left: 20px;'>");
        
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                String processedLine = line.trim();
                
                if (!processedLine.startsWith("-") && !processedLine.startsWith("•")) {
                    processedLine = "<strong>" + processedLine + "</strong>";
                }
                processedLine = processedLine.replaceFirst("- ", "<li>");

                if (!processedLine.startsWith("<li>")) {
                    processedLine = "<li>" + processedLine + "</li>";
                } else {
                    processedLine += "</li>";
                }
                
                sb.append(processedLine);
            }
        }
        sb.append("</ul>");
        return sb.toString();
    }

    // --- HTML GENERATION METHOD (Professional Design) ---
    void generateResumeHTML() {
        // Use current field content or display the blank template placeholders
        String name = nameField.getText().isEmpty() ? "YOUR NAME HERE" : nameField.getText();
        String email = emailField.getText().isEmpty() ? "your.email@example.com" : emailField.getText();
        String phone = phoneField.getText().isEmpty() ? "###-###-####" : phoneField.getText();
        
        String skillsHtml = convertToHtmlList(skillsArea.getText(), "none");
        String educationHtml = convertToHtmlList(eduArea.getText(), "none");
        String experienceHtml = convertToHtmlList(expArea.getText(), "disc");
        String projectsHtml = convertToHtmlList(projectsArea.getText(), "none"); // PROCESS NEW FIELD

        // Set content if blank (using placeholders for initial display)
        if (skillsArea.getText().isEmpty()) skillsHtml = "<p style='padding-left: 20px;'>*Required: Python, Java, SQL, MS-Word.</p>";
        if (eduArea.getText().isEmpty()) educationHtml = "<p style='padding-left: 20px;'>*Required: Degree, Institution (Year Range)</p>";
        if (expArea.getText().isEmpty()) experienceHtml = "<p style='padding-left: 20px;'>*Required: Job Title, Company (Year Range) - Key accomplishment/duty 1...</p>";
        if (projectsArea.getText().isEmpty()) projectsHtml = "<p style='padding-left: 20px;'>*Required: Project Name, Technologies Used (e.g., Image Processor in Python).</p>";
        
        String objectiveText = nameField.getText().isEmpty() 
            ? "Enter a precise, 2-3 sentence summary of your career focus and value proposition. Tailor this section for every job application." 
            : "Your personal career objective statement goes here. Keep it short and impactful.";


        String htmlContent = "<html><head><title>Resume - " + name + "</title>" +
            "<style>" +
            "body { font-family: 'Times New Roman', Times, serif; margin: 0; padding: 30px; color: #333; line-height: 1.4; font-size: 11pt; }" +
            ".header { background-color: #ffffff; color: #2c3e50; padding: 0 0 10px 0; text-align: center; margin-bottom: 20px; border-bottom: 3px solid #2c3e50; }" +
            ".header h1 { margin: 0; font-size: 20pt; font-weight: 700; }" +
            ".contact { margin-top: 5px; font-size: 10pt; color: #7f8c8d; }" +
            "h2 { color: #2c3e50; border-bottom: 2px solid #ecf0f1; padding-bottom: 5px; margin-top: 20px; font-size: 14pt; font-weight: 600; }" +
            "ul { margin-top: 5px; margin-bottom: 5px; }" +
            "li { margin-bottom: 8px; line-height: 1.3; }" +
            "</style></head><body>" +

            // Name and Contact (Styled Header)
            "<div class='header'>" +
            "<h1>" + name.toUpperCase() + "</h1>" +
            "<div class='contact'>" + email + " | " + phone + " | Location/LinkedIn/Portfolio</div>" +
            "</div>" +
            
            // Career Objective
            "<h2>CAREER OBJECTIVE</h2>" +
            "<div>" + objectiveText + "</div>" +

            // Work Experience
            "<h2>WORK EXPERIENCE</h2>" +
            "<div>" + experienceHtml + "</div>" +

            // Education
            "<h2>EDUCATION</h2>" +
            "<div>" + educationHtml + "</div>" +
            
            // Projects
            "<h2>PROJECTS</h2>" +
            "<div>" + projectsHtml + "</div>" + // DISPLAY NEW FIELD
            
            // Skills
            "<h2>SKILLS</h2>" +
            "<div>" + skillsHtml + "</div>" +
            
            // Certifications (Placeholder Section)
            "<h2>CERTIFICATIONS / AWARDS</h2>" +
            "<div>" +
            "Use this section for key certifications and awards. (Add details in the 'Education' or 'Experience' box if preferred)." +
            "</div>" +
            
            "</body></html>";

        previewArea.setText(htmlContent);
    }
    
    // Updated method for saving HTML
    void saveResumeHTML() {
        // Validation check is now performed by saveOrValidateResume()
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Styled Resume As...");
        fileChooser.setSelectedFile(new File("MyProfessionalResume.html"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try (FileWriter writer = new FileWriter(fileToSave)) {
                        writer.write(previewArea.getText());
                    }
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        get();
                        JOptionPane.showMessageDialog(resumebuilder.this,
                                "Resume saved successfully as HTML to " + fileToSave.getAbsolutePath() + 
                                "\n\nNEXT STEP: Open this file in a browser (Ctrl+O) and use Ctrl+P to 'Save as PDF'.",
                                "Save Successful - PDF Ready", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(resumebuilder.this,
                                "Error saving file: " + ex.getMessage(),
                                "Save Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
        }
    }

    void resetFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        skillsArea.setText("");
        eduArea.setText("");
        expArea.setText("");
        projectsArea.setText(""); // RESET NEW FIELD
        generateResumeHTML(); // Regenerate the empty template
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Ignore if L&F can't be set
        }
        SwingUtilities.invokeLater(() -> new resumebuilder().setVisible(true)); 
    }
}