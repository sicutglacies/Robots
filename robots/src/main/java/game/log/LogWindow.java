package game.log;

import game.views.locale.LocaleChangeListener;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.util.ResourceBundle;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import static game.MainApplicationFrame.bundle;

public class LogWindow extends JInternalFrame implements LogChangeListener, LocaleChangeListener {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindow(LogWindowSource logSource) {
        super(bundle.getString("protocol"), true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setLocation(10, 10);
        m_logContent.setSize(300, 800);
        setMinimumSize(m_logContent.getSize());
        Logger.debug(bundle.getString("protoWork"));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public void onLocaleChanged(ResourceBundle bundle) {
        this.title = bundle.getString("protocol");
        this.repaint();
    }
}