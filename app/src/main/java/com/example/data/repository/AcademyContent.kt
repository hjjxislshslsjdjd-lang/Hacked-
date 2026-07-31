package com.example.data.repository

import com.example.data.model.Badge
import com.example.data.model.CtfChallenge
import com.example.data.model.LevelEnum
import com.example.data.model.Module
import com.example.data.model.Quiz
import com.example.data.model.QuizQuestion
import com.example.data.model.UserRank

object AcademyContent {

    val RANKS = listOf(
        UserRank("Cyber Cadet", "Beginner Level 1", 0, 250, "🔰"),
        UserRank("Net Security Analyst", "Beginner Level 2", 251, 650, "🛡️"),
        UserRank("Ethical Hacker Specialist", "Intermediate Level", 651, 1250, "🔍"),
        UserRank("Penetration Tester Pro", "Advanced Level", 1251, 2000, "⚔️"),
        UserRank("Cyber Sentinel Master", "Elite Master", 2001, 10000, "👑")
    )

    val BADGES = listOf(
        Badge("b_first_step", "First Shield", "Completed your very first cybersecurity lesson", "🛡️", "Complete 1 Lesson"),
        Badge("b_beg_master", "Beginner Sentinel", "Completed all Beginner level modules", "🎓", "Complete Beginner Level"),
        Badge("b_ctf_solver", "Flag Hunter", "Solved your first CTF challenge", "🚩", "Solve 1 CTF Challenge"),
        Badge("b_quiz_100", "Quiz Master", "Achieved 100% score on a level quiz", "🎯", "Score 100% on a Quiz"),
        Badge("b_crypto", "Cipher Decrypter", "Decoded an encrypted CTF payload", "🔐", "Solve Cryptography CTF"),
        Badge("b_web_guard", "Web Defense Specialist", "Mastered web security and SQLi defense", "🌐", "Complete Web Security"),
        Badge("b_streak_3", "Cyber Vanguard", "Maintained learning streak for 3 consecutive days", "🔥", "3 Day Streak"),
        Badge("b_adv_master", "Master Ethical Hacker", "Unlocked and mastered Advanced Level", "👑", "Complete Advanced Level")
    )

    val MODULES = listOf(
        // BEGINNER LEVEL
        Module(
            id = "beg_1",
            level = LevelEnum.BEGINNER,
            title = "What is Hacking?",
            description = "Fundamental definition of hacking, history, mindsets, legal boundaries, and ethical guidelines.",
            readTimeMinutes = 4,
            drawableResName = "img_cyber_hero_1785521647320",
            topics = listOf("Definition & History", "Hacker Mindset", "Ethical vs Malicious", "Legal Frameworks"),
            contentMarkdown = """
# What is Hacking?

Hacking is the process of exploring, testing, and modifying technology systems (computers, networks, software) to understand how they function or overcome limitations.

### History & Evolution
Originally born in MIT computer clubs in the 1960s, "hacking" meant clever engineering solutions. Over decades, as global banking and communications migrated online, computer security became vital.

### Ethical Hacking vs Malicious Hacking
• **Ethical Hacking**: Finding vulnerabilities to fix them before bad actors exploit them. Always performed with written authorization.
• **Malicious Hacking**: Unauthorized exploitation of systems for theft, destruction, or ransom.

### The Golden Rules of Ethical Hacking
1. **Never hack without explicit written authorization.**
2. Respect privacy and data confidentiality.
3. Report all identified vulnerabilities responsibly.
4. Minimize impact during security testing.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Ethical hacking requires explicit written authorization.",
                "The core goal of an ethical hacker is protecting systems.",
                "Responsible disclosure helps fix security flaws safely."
            )
        ),
        Module(
            id = "beg_2",
            level = LevelEnum.BEGINNER,
            title = "Types of Hackers",
            description = "Understanding White Hat, Black Hat, Grey Hat, and cybersecurity team roles.",
            readTimeMinutes = 5,
            drawableResName = "img_security_team",
            topics = listOf("White Hat", "Black Hat", "Grey Hat", "Red / Blue / Purple Teams"),
            contentMarkdown = """
# Types of Hackers

Security professionals categorize hackers into distinct hats based on intent, authorization, and legality.

### 1. White Hat Hacker (Ethical Hacker)
Authorized security researchers, penetration testers, and security auditors who use hacking skills to defend organizations and user data.

### 2. Black Hat Hacker
Unauthorized cybercriminals who break into computer systems with malicious intent—stealing credentials, deploying ransomware, or disrupting critical infrastructure.

### 3. Grey Hat Hacker
Operators who fall between black and white hats. They may discover vulnerabilities without permission, but inform the system owners without intent to cause harm. (Still illegal in many jurisdictions without consent).

### Security Operations Teams
• **Red Team**: Offensive security testers who simulate real cyberattacks.
• **Blue Team**: Defensive defenders who monitor, detect, and respond to incidents.
• **Purple Team**: Collaborative integration between Red and Blue teams.
            """.trimIndent(),
            keyTakeaways = listOf(
                "White hats operate legally with permission; Black hats commit illegal attacks.",
                "Red Teams attack; Blue Teams defend; Purple Teams collaborate.",
                "Operating without authorization is illegal, regardless of intent."
            )
        ),
        Module(
            id = "beg_3",
            level = LevelEnum.BEGINNER,
            title = "Cybersecurity Basics",
            description = "The CIA Triad, Threat Vectors, Vulnerabilities, and Risk Management.",
            readTimeMinutes = 5,
            drawableResName = "img_digital_lock_1785521661440",
            topics = listOf("CIA Triad", "Threat vs Vulnerability vs Risk", "Defense in Depth"),
            contentMarkdown = """
# Cybersecurity Basics

Every security strategy is built around foundational concepts that guide risk reduction.

### The CIA Triad
• **Confidentiality**: Ensuring data is accessible ONLY to authorized users (e.g., Encryption, Access Control).
• **Integrity**: Protecting data against unauthorized modification or tampering (e.g., Digital Signatures, Hashing).
• **Availability**: Guaranteeing systems and data are reliable and accessible when needed (e.g., Redundancy, DDoS Mitigation).

### Threat, Vulnerability, and Risk
• **Vulnerability**: A flaw or weakness in software/hardware (e.g., unpatched software).
• **Threat**: Anything that can exploit a vulnerability (e.g., malware, attacker).
• **Risk**: The likelihood and impact of a threat exploiting a vulnerability (`Risk = Threat x Vulnerability x Impact`).

### Defense in Depth
Never rely on a single layer of security. Use multiple layered defenses: Firewalls + Endpoint Antivirus + Multi-Factor Auth + User Education.
            """.trimIndent(),
            keyTakeaways = listOf(
                "CIA stands for Confidentiality, Integrity, and Availability.",
                "Risk is the product of Threat, Vulnerability, and Business Impact.",
                "Defense in Depth uses multiple overlapping security layers."
            )
        ),
        Module(
            id = "beg_4",
            level = LevelEnum.BEGINNER,
            title = "Internet & Network Basics",
            description = "IP addresses, MAC addresses, TCP/UDP protocols, DNS, and packet routing.",
            readTimeMinutes = 6,
            drawableResName = "img_network_diagram_1785521676981",
            topics = listOf("IPv4 vs IPv6", "MAC Addresses", "TCP vs UDP", "DNS & Routing"),
            contentMarkdown = """
# Internet & Network Basics

To protect or test a network, you must understand how devices communicate across the internet.

### Network Addressing
• **IP Address (Logical)**: Identifies a device on a network (e.g., IPv4 `192.168.1.1` or IPv6).
• **MAC Address (Physical)**: Unique hardware burn-in address (e.g., `00:1A:2B:3C:4D:5E`).

### Core Transport Protocols
• **TCP (Transmission Control Protocol)**: Connection-oriented, reliable 3-way handshake (`SYN` -> `SYN-ACK` -> `ACK`). Used for Web (HTTP/S), Email, SSH.
• **UDP (User Datagram Protocol)**: Connectionless, fast, no guaranteed delivery. Used for Video streaming, Gaming, DNS lookups.

### Domain Name System (DNS)
Acts as the internet's phonebook, translating human domain names (`example.com`) into numeric IP addresses (`93.184.216.34`).
            """.trimIndent(),
            keyTakeaways = listOf(
                "TCP is reliable (handshake); UDP is fast (connectionless).",
                "IP addresses are logical; MAC addresses are hardware physical.",
                "DNS translates human domain names into IP addresses."
            )
        ),
        Module(
            id = "beg_5",
            level = LevelEnum.BEGINNER,
            title = "Strong Password Concepts",
            description = "Entropy, hashing, salting, password managers, and Multi-Factor Authentication (MFA).",
            readTimeMinutes = 5,
            drawableResName = "img_code_terminal_1785521692944",
            topics = listOf("Password Length & Entropy", "Hashing & Salting", "Password Managers", "2FA / MFA"),
            contentMarkdown = """
# Strong Password Concepts

Passwords remain the primary authentication gatekeeper. Weak passwords are the #1 entry point for initial access.

### Length Over Complexity (Entropy)
Length increases entropy exponentially. A 16-character passphrase (`correct-horse-battery-staple`) takes centuries to brute-force compared to a complex 8-character password (`P@ss1234`).

### Hashing & Salting
Passwords should NEVER be stored as plain text in databases.
• **Cryptographic Hash**: One-way conversion (e.g., SHA-256, bcrypt).
• **Salt**: Random data added to passwords before hashing to prevent Rainbow Table attacks.

### Multi-Factor Authentication (MFA)
MFA requires 2 or more factors:
1. **Something you know**: Password / PIN
2. **Something you have**: Authenticator app token / Security Key
3. **Something you are**: Biometric fingerprint / Face ID
            """.trimIndent(),
            keyTakeaways = listOf(
                "Passphrases (16+ chars) offer higher security than short complex passwords.",
                "Salting hashes prevents pre-computed rainbow table attacks.",
                "Enabling MFA blocks up to 99% of automated account takeover attacks."
            )
        ),

        // INTERMEDIATE LEVEL
        Module(
            id = "int_1",
            level = LevelEnum.INTERMEDIATE,
            title = "Phishing Awareness",
            description = "Detecting email phishing, spear phishing, smishing, vishing, and URL spoofing.",
            readTimeMinutes = 6,
            drawableResName = "img_cyber_hero_1785521647320",
            topics = listOf("Phishing Vectors", "Spear Phishing", "URL Spoofing", "Header Analysis"),
            contentMarkdown = """
# Phishing Awareness

Phishing is a social engineering attack used to steal credentials or deliver malware via deceptive communications.

### Types of Phishing
• **Email Phishing**: Mass emails impersonating trusted brands (banks, Microsoft, Google).
• **Spear Phishing**: Targeted attacks customized for a specific person or executive (CEO Fraud).
• **Smishing & Vishing**: Phishing via SMS text messages or phone calls.

### Key Indicators of Phishing Emails
1. **Spoofed Sender Address**: e.g., `security@paypa1-support.com` instead of `@paypal.com`.
2. **Artificial Sense of Urgency**: "Account suspended within 1 hour unless verified!"
3. **Mismatched Link Destination**: Button text shows `https://bank.com`, but hovering reveals `http://evil-phish.net`.
4. **Generic Greetings**: "Dear Customer" instead of your actual name.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Always verify sender domains and hover over links before clicking.",
                "Be suspicious of artificial sense of urgency and threat of account closure.",
                "Use Email Authentication standards like SPF, DKIM, and DMARC."
            )
        ),
        Module(
            id = "int_2",
            level = LevelEnum.INTERMEDIATE,
            title = "Social Engineering Concepts",
            description = "Human psychology exploits: Pretexting, Tailgating, Baiting, Impersonation.",
            readTimeMinutes = 5,
            drawableResName = "img_security_team",
            topics = listOf("Pretexting", "Tailgating", "Baiting", "Quid Pro Quo"),
            contentMarkdown = """
# Social Engineering Concepts

Social engineering exploits human psychology rather than technical software bugs to gain unauthorized access.

### Common Tactics
• **Pretexting**: Creating a fabricated scenario (e.g., calling as "IT Helpdesk" asking for password reset).
• **Tailgating**: Physically following an authorized employee into a secured building door.
• **Baiting**: Leaving infected USB drives labeled "Executive Salaries Q3" in public parking lots.
• **Quid Pro Quo**: Offering a service (e.g. free tech support) in exchange for credentials.

### Defense Mechanisms
• Strict Security Awareness Training.
• Physical Badge Scanning doors & Security Guards.
• Strict verification policies—NEVER share passwords with IT support.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Humans are often the weakest link in the security chain.",
                "Never hold doors open without badge verification (tailgating defense).",
                "Never plug unknown USB devices into company machines."
            )
        ),
        Module(
            id = "int_3",
            level = LevelEnum.INTERMEDIATE,
            title = "Malware Awareness",
            description = "Viruses, Worms, Trojans, Ransomware, Spyware, Rootkits, and Keyloggers.",
            readTimeMinutes = 6,
            drawableResName = "img_code_terminal_1785521692944",
            topics = listOf("Ransomware", "Trojans", "Worms", "Rootkits", "Static/Dynamic Analysis"),
            contentMarkdown = """
# Malware Awareness

Malware (Malicious Software) is any software designed to disrupt, damage, or gain unauthorized access to a computer system.

### Malware Types
• **Ransomware**: Encrypts files and demands payment for the decryption key (e.g. WannaCry).
• **Trojan**: Disguises itself as legitimate software (e.g. a free game) but contains hidden malicious payloads.
• **Worm**: Self-replicating malware that spreads across networks without user interaction.
• **Rootkit**: Obtains kernel/root privileges and hides its presence from Antivirus tools.
• **Spyware / Keylogger**: Records keystrokes, screenshots, and credentials silently.

### Detection Methods
• **Signature-Based**: Compares file hashes against known malware databases.
• **Heuristic / Behavioral**: Detects suspicious process behaviors (e.g., process injection).
            """.trimIndent(),
            keyTakeaways = listOf(
                "Ransomware encrypts files; backups are the most critical recovery defense.",
                "Worms spread automatically across networks; Trojans require user execution.",
                "Keep endpoint security tools updated with real-time heuristic detection."
            )
        ),
        Module(
            id = "int_4",
            level = LevelEnum.INTERMEDIATE,
            title = "Network Security Basics",
            description = "Firewalls, IDS/IPS, VPNs, Wi-Fi security (WPA3), and Port Scanning.",
            readTimeMinutes = 6,
            drawableResName = "img_network_diagram_1785521676981",
            topics = listOf("Firewalls (Stateful & WAF)", "IDS vs IPS", "VPN Encapsulation", "Wi-Fi Security"),
            contentMarkdown = """
# Network Security Basics

Securing a network requires controlling traffic flow and monitoring for abnormal packet transmissions.

### Firewalls & WAF
• **Network Firewall**: Filters traffic based on IP addresses, ports, and protocols (Stateful Inspection).
• **Web Application Firewall (WAF)**: Filters HTTP/HTTPS traffic to block web attacks like SQLi and XSS.

### IDS vs IPS
• **IDS (Intrusion Detection System)**: Monitors network traffic and alerts administrators when attacks occur.
• **IPS (Intrusion Prevention System)**: Actively drops or blocks malicious traffic in real time.

### Virtual Private Networks (VPNs)
Establishes an encrypted tunnel between client and server, protecting data over untrusted public Wi-Fi.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Firewalls block unauthorized ports and traffic.",
                "IDS alerts on threats; IPS actively blocks threats.",
                "WPA3 provides strong Wi-Fi encryption over legacy WPA2."
            )
        ),
        Module(
            id = "int_5",
            level = LevelEnum.INTERMEDIATE,
            title = "Web Security Concepts",
            description = "HTTP vs HTTPS, Cookies, Sessions, and OWASP Top 10 introduction.",
            readTimeMinutes = 7,
            drawableResName = "img_digital_lock_1785521661440",
            topics = listOf("HTTPS / TLS", "Cookie Security Flags", "OWASP Top 10", "XSS & SQLi Intro"),
            contentMarkdown = """
# Web Security Concepts

Web applications power global e-commerce, banking, and SaaS. Securing web traffic and code is essential.

### HTTP vs HTTPS (TLS/SSL)
• **HTTP**: Plaintext communications—vulnerable to eavesdropping and Man-in-the-Middle (MitM) attacks.
• **HTTPS**: Encrypted using TLS (Transport Layer Security) with digital certificates.

### Secure Cookies & Sessions
Web cookies maintain user session state. Secure cookie attributes:
• `HttpOnly`: Prevents client-side JavaScript from accessing cookies (blocks XSS cookie theft).
• `Secure`: Ensures cookies are transmitted ONLY over HTTPS.
• `SameSite=Strict`: Prevents Cross-Site Request Forgery (CSRF) attacks.

### OWASP Top 10 Overview
Maintained by OWASP, listing top web vulnerabilities: Injection, Broken Auth, Sensitive Data Exposure, Broken Access Control, Security Misconfiguration.
            """.trimIndent(),
            keyTakeaways = listOf(
                "HTTPS encrypts web traffic using TLS certificates.",
                "HttpOnly and Secure flags protect session cookies.",
                "OWASP Top 10 provides the standard benchmark for web application security."
            )
        ),
        Module(
            id = "int_6",
            level = LevelEnum.INTERMEDIATE,
            title = "Vulnerability Identification",
            description = "CVE, CVSS scoring, banner grabbing, and patch management.",
            readTimeMinutes = 5,
            drawableResName = "img_code_terminal_1785521692944",
            topics = listOf("CVE Registry", "CVSS Scores", "Banner Grabbing", "Patch Management"),
            contentMarkdown = """
# Vulnerability Identification

Discovering security vulnerabilities before bad actors exploit them is a core security function.

### CVE (Common Vulnerabilities and Exposures)
A standardized dictionary of publicly known cybersecurity vulnerabilities (e.g., `CVE-2021-44228` Log4Shell).

### CVSS (Common Vulnerability Scoring System)
Scores severity from 0.0 to 10.0:
• **Low**: 0.1 – 3.9
• **Medium**: 4.0 – 6.9
• **High**: 7.0 – 8.9
• **Critical**: 9.0 – 10.0

### Banner Grabbing
Connecting to open ports on a server to determine software versions (e.g., `Apache 2.4.41` on port 80).
            """.trimIndent(),
            keyTakeaways = listOf(
                "CVE provides standardized IDs for software vulnerabilities.",
                "CVSS rates severity from 0 to 10.",
                "Timely patch management mitigates known CVEs."
            )
        ),
        Module(
            id = "int_7",
            level = LevelEnum.INTERMEDIATE,
            title = "Security Best Practices",
            description = "Least Privilege, Zero Trust, 3-2-1 Backups, and System Hardening.",
            readTimeMinutes = 5,
            drawableResName = "img_cyber_hero_1785521647320",
            topics = listOf("Principle of Least Privilege", "Zero Trust", "3-2-1 Backup Rule", "System Hardening"),
            contentMarkdown = """
# Security Best Practices

Actionable operational principles to build resilient organization security posture.

### Principle of Least Privilege (PoLP)
Users and processes should only be granted the minimum necessary permissions required to perform their tasks.

### 3-2-1 Backup Strategy
• **3** copies of critical data.
• **2** different storage media types (e.g. Cloud + Local Disk).
• **1** offsite / immutable air-gapped copy (protected from ransomware).

### System Hardening
Disabling unused services, removing default accounts/passwords, enforcing firewall rules, and enabling automatic security patches.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Grant minimum required permissions (Least Privilege).",
                "Maintain 3 copies of data on 2 media with 1 offsite copy.",
                "System hardening removes default passwords and closes unused ports."
            )
        ),

        // ADVANCED LEVEL
        Module(
            id = "adv_1",
            level = LevelEnum.ADVANCED,
            title = "Ethical Hacking Methodology",
            description = "The 5 phases: Reconnaissance, Scanning, Gaining Access, Maintaining Access, Reporting.",
            readTimeMinutes = 7,
            drawableResName = "img_code_terminal_1785521692944",
            topics = listOf("Reconnaissance", "Scanning & Enumeration", "Gaining Access", "Reporting"),
            contentMarkdown = """
# Ethical Hacking Methodology

Professional ethical hacking follows a systematic 5-phase framework during security assessments.

### Phase 1: Reconnaissance (Information Gathering)
• **Passive Recon**: OSINT (Open Source Intelligence), WHOIS, Shodan, Google Dorking. No direct contact with target.
• **Active Recon**: Port scanning, DNS zone transfers, web crawler discovery.

### Phase 2: Scanning & Enumeration
Identifying live hosts, open ports, running OS services, and software version numbers.

### Phase 3: Gaining Access (Exploitation)
Exploiting verified vulnerabilities (e.g., buffer overflow, web injection, default credentials) to gain initial system shell access.

### Phase 4: Maintaining Access & Post-Exploitation
Privilege escalation (Local Admin/Root), persistence evaluation, and lateral movement.

### Phase 5: Clearing Tracks & Reporting
In ethical testing, tracks are NOT erased to destroy logs; instead, testing artifacts are removed and a detailed executive/technical report is delivered.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Passive recon gathers data without contacting the target.",
                "Exploitation converts a vulnerability into initial access.",
                "A comprehensive report with remediation recommendations is the ultimate deliverable."
            )
        ),
        Module(
            id = "adv_2",
            level = LevelEnum.ADVANCED,
            title = "Penetration Testing Concepts",
            description = "Black-box vs White-box vs Grey-box testing, Rules of Engagement, Scope.",
            readTimeMinutes = 6,
            drawableResName = "img_security_team",
            topics = listOf("Black-Box vs White-Box", "Rules of Engagement (RoE)", "Scope Definition"),
            contentMarkdown = """
# Penetration Testing Concepts

Penetration testing is a simulated cyberattack authorized to evaluate system defenses.

### Testing Methodologies
• **Black-Box Testing**: Tester has ZERO prior knowledge of internal code or architecture (simulates real external attacker).
• **White-Box Testing**: Tester has FULL access to source code, network diagrams, and credentials (thorough code audit).
• **Grey-Box Testing**: Tester has partial knowledge (e.g., standard user credentials).

### Rules of Engagement (RoE)
Legal contract defining permitted testing windows, IP ranges in scope, prohibited actions (e.g., no DoS testing on production), emergency contact numbers, and liability caps.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Black-box = zero prior knowledge; White-box = full source code access.",
                "RoE strictly defines legal boundaries and emergency contacts.",
                "Testing outside the agreed scope is illegal."
            )
        ),
        Module(
            id = "adv_3",
            level = LevelEnum.ADVANCED,
            title = "Security Auditing & Compliance",
            description = "ISO 27001, NIST Cybersecurity Framework, SOC 2, and SIEM log analysis.",
            readTimeMinutes = 6,
            drawableResName = "img_digital_lock_1785521661440",
            topics = listOf("NIST Framework", "ISO 27001", "SOC 2 Type II", "SIEM Log Analysis"),
            contentMarkdown = """
# Security Auditing & Compliance

Auditing ensures systems meet mandatory regulatory standards and internal security policies.

### NIST Cybersecurity Framework (CSF 2.0)
5 Core Functions:
1. **Govern / Identify**: Asset management, risk assessment.
2. **Protect**: Safeguards, access control, encryption.
3. **Detect**: Continuous monitoring, anomaly detection.
4. **Respond**: Incident response execution.
5. **Recover**: System restoration & post-incident review.

### SIEM (Security Information and Event Management)
Centralized logging platform (Splunk, Elastic, Sentinel) that aggregates log data from firewalls, servers, and endpoints to detect correlates attacks in real time.
            """.trimIndent(),
            keyTakeaways = listOf(
                "NIST CSF provides 5 core functions for enterprise security posture.",
                "SIEM aggregates and correlates security logs from across the organization.",
                "Compliance audits verify adherence to frameworks like ISO 27001 and SOC 2."
            )
        ),
        Module(
            id = "adv_4",
            level = LevelEnum.ADVANCED,
            title = "Vulnerability Assessment",
            description = "Automated vulnerability scanners vs Manual testing, False Positives, and Remediation.",
            readTimeMinutes = 5,
            drawableResName = "img_code_terminal_1785521692944",
            topics = listOf("Vulnerability Scanners", "Manual Validation", "False Positives", "Remediation SLAs"),
            contentMarkdown = """
# Vulnerability Assessment

Systematic review of security weaknesses in an information system.

### Automated Scanners vs Manual Testing
• **Automated Scanners** (Nessus, Qualys, OpenVAS): Quickly scan thousands of IP addresses for known CVE signatures.
• **Manual Validation**: Security analysts manually verify scanner findings to eliminate **False Positives** (incorrectly reported flaws).

### Remediation Service Level Agreements (SLAs)
• **Critical Severity (CVSS 9.0+)**: Patch within 24 - 72 hours.
• **High Severity (CVSS 7.0 - 8.9)**: Patch within 7 - 14 days.
• **Medium Severity**: Patch within 30 days.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Automated scanners identify potential flaws; manual testing eliminates false positives.",
                "Prioritize remediation based on CVSS severity and asset critical nature."
            )
        ),
        Module(
            id = "adv_5",
            level = LevelEnum.ADVANCED,
            title = "Capture The Flag (CTF) Mechanics",
            description = "Jeopardy vs Attack-Defense styles, Flag formats, Cryptography, and Reverse Engineering.",
            readTimeMinutes = 6,
            drawableResName = "img_network_diagram_1785521676981",
            topics = listOf("Jeopardy CTF", "Flag Format", "Cryptography Basics", "Reverse Engineering"),
            contentMarkdown = """
# Capture The Flag (CTF) Mechanics

CTFs are cybersecurity competitions designed to test practical hands-on problem solving skills.

### CTF Formats
• **Jeopardy Style**: Categories (Web, Crypto, Forensics, Reverse Engineering, Pwn) with points awarded for submitting correct flags.
• **Attack-Defense Style**: Teams defend their own vulnerable servers while hacking competitors' servers.

### Common CTF Categories
1. **Cryptography**: Deciphering encryptions (Caesar, Base64, RSA, AES).
2. **Web Exploitation**: Inspecting source code, headers, SQLi, XSS, JWT tampering.
3. **Digital Forensics**: Memory dumps, Wireshark packet captures (`.pcap`), steganography.
4. **Reverse Engineering**: Analyzing compiled binaries (Ghidra, IDA Pro).
            """.trimIndent(),
            keyTakeaways = listOf(
                "CTF flags follow formats like `flag{secret_text_here}`.",
                "Jeopardy CTFs test Web, Crypto, Forensics, and Binary Exploitation.",
                "CTF challenges sharpen real-world ethical hacking skills."
            )
        ),
        Module(
            id = "adv_6",
            level = LevelEnum.ADVANCED,
            title = "Defensive Security Techniques",
            description = "Incident Response lifecycle, Threat Hunting, HoneyPots, EDR, and Hardening.",
            readTimeMinutes = 6,
            drawableResName = "img_cyber_hero_1785521647320",
            topics = listOf("Incident Response (PICERL)", "Threat Hunting", "Honeypots", "EDR"),
            contentMarkdown = """
# Defensive Security Techniques

Defensive security (Blue Teaming) focuses on proactive threat detection, containment, and recovery.

### Incident Response Lifecycle (PICERL)
1. **Preparation**: Polices, tools, training.
2. **Identification**: Detecting compromised assets.
3. **Containment**: Isolating infected systems from the network.
4. **Eradication**: Removing malware and backdoor access.
5. **Recovery**: Restoring clean backups to production.
6. **Lessons Learned**: Root cause analysis report.

### Honeypots
Decoy targets setup deliberately to attract attackers, gather threat intelligence, and delay intrusion.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Containment isolates compromised devices immediately.",
                "Honeypots trap attackers to study TTPs (Tactics, Techniques, and Procedures).",
                "EDR provides deep process visibility on endpoints."
            )
        )
    )

    val QUIZZES = listOf(
        Quiz(
            id = "quiz_beg",
            level = LevelEnum.BEGINNER,
            title = "Beginner Cybersecurity Assessment",
            description = "Test your knowledge on White/Black hats, CIA triad, TCP/UDP, and password entropy.",
            questions = listOf(
                QuizQuestion(
                    id = "q_b1",
                    question = "Which type of hacker operates legally with explicit written authorization?",
                    options = listOf("Black Hat Hacker", "White Hat Hacker (Ethical Hacker)", "Grey Hat Hacker", "Script Kiddie"),
                    correctOptionIndex = 1,
                    explanation = "White Hat hackers are authorized security professionals who test systems with permission to fix vulnerabilities."
                ),
                QuizQuestion(
                    id = "q_b2",
                    question = "What does the 'C' in the CIA Triad stand for?",
                    options = listOf("Control", "Compliance", "Confidentiality", "Cipher"),
                    correctOptionIndex = 2,
                    explanation = "CIA stands for Confidentiality, Integrity, and Availability."
                ),
                QuizQuestion(
                    id = "q_b3",
                    question = "Which transport protocol guarantees reliable delivery via a 3-way handshake?",
                    options = listOf("UDP", "TCP", "ICMP", "ARP"),
                    correctOptionIndex = 1,
                    explanation = "TCP (Transmission Control Protocol) is connection-oriented and uses a SYN -> SYN-ACK -> ACK handshake."
                ),
                QuizQuestion(
                    id = "q_b4",
                    question = "What is the most effective factor in increasing password entropy and strength?",
                    options = listOf("Adding 1 special symbol", "Increasing overall password length (e.g. 16+ character passphrase)", "Changing it every 2 days", "Capitalizing the first letter"),
                    correctOptionIndex = 1,
                    explanation = "Length increases entropy exponentially, making long passphrases far stronger against brute-force attacks."
                )
            )
        ),
        Quiz(
            id = "quiz_int",
            level = LevelEnum.INTERMEDIATE,
            title = "Intermediate Security & Defense Quiz",
            description = "Test your skills on phishing detection, malware types, firewalls, and OWASP web security.",
            questions = listOf(
                QuizQuestion(
                    id = "q_i1",
                    question = "Which malware type encrypts victim files and demands payment for the decryption key?",
                    options = listOf("Trojan", "Ransomware", "Keylogger", "Worm"),
                    correctOptionIndex = 1,
                    explanation = "Ransomware encrypts victim data and demands payment (often cryptocurrency) for decryption."
                ),
                QuizQuestion(
                    id = "q_i2",
                    question = "Which cookie attribute prevents client-side JavaScript from accessing session cookies, mitigating cookie theft via XSS?",
                    options = listOf("Secure", "HttpOnly", "SameSite", "Domain"),
                    correctOptionIndex = 1,
                    explanation = "The HttpOnly flag blocks JavaScript (document.cookie) from accessing session tokens."
                ),
                QuizQuestion(
                    id = "q_i3",
                    question = "What is the primary difference between an IDS and an IPS?",
                    options = listOf("IDS uses AI; IPS uses rule files", "IDS monitors & alerts; IPS actively blocks malicious traffic", "IDS works on Wi-Fi; IPS works on Ethernet", "There is no difference"),
                    correctOptionIndex = 1,
                    explanation = "IDS (Detection System) alerts on threats; IPS (Prevention System) inline drops/blocks malicious traffic."
                ),
                QuizQuestion(
                    id = "q_i4",
                    question = "What CVSS score range represents a Critical severity vulnerability?",
                    options = listOf("0.0 - 3.9", "4.0 - 6.9", "7.0 - 8.9", "9.0 - 10.0"),
                    correctOptionIndex = 3,
                    explanation = "CVSS scores from 9.0 to 10.0 are rated Critical and require urgent patching."
                )
            )
        ),
        Quiz(
            id = "quiz_adv",
            level = LevelEnum.ADVANCED,
            title = "Advanced Ethical Hacking & CTF Mastery",
            description = "Evaluate mastery in pentesting phases, RoE, NIST framework, and SQLi mitigation.",
            questions = listOf(
                QuizQuestion(
                    id = "q_a1",
                    question = "In penetration testing, what is 'Black-Box' testing?",
                    options = listOf("Tester has full source code access", "Tester has ZERO prior knowledge of internal target details", "Testing performed only on dark web servers", "Testing restricted to hardware routers"),
                    correctOptionIndex = 1,
                    explanation = "Black-box testing simulates an external attacker with zero prior knowledge of internal target infrastructure."
                ),
                QuizQuestion(
                    id = "q_a2",
                    question = "What is the primary technical defense against SQL Injection (SQLi)?",
                    options = listOf("Encrypting database hard drives", "Using Parameterized Queries (Prepared Statements)", "Using complex passwords", "Hiding the database port"),
                    correctOptionIndex = 1,
                    explanation = "Parameterized queries separate code from data, preventing untrusted input from modifying SQL query structure."
                ),
                QuizQuestion(
                    id = "q_a3",
                    question = "Which document defines legal boundaries, emergency contacts, and scope during a penetration test?",
                    options = listOf("CVE Dictionary", "Rules of Engagement (RoE)", "SSL Certificate", "WHOIS Record"),
                    correctOptionIndex = 1,
                    explanation = "The Rules of Engagement (RoE) is the legal contract specifying authorized testing scope and rules."
                ),
                QuizQuestion(
                    id = "q_a4",
                    question = "In the PICERL Incident Response framework, what step immediately follows Identification?",
                    options = listOf("Preparation", "Containment", "Eradication", "Recovery"),
                    correctOptionIndex = 1,
                    explanation = "Containment immediately follows Identification to isolate compromised assets and prevent threat spread."
                )
            )
        )
    )

    val CTF_CHALLENGES = listOf(
        CtfChallenge(
            id = "ctf_1",
            level = LevelEnum.BEGINNER,
            title = "Base64 Transmission Decoder",
            category = "Cryptography",
            difficulty = "Easy",
            description = "Intercepted an encoded communication payload from a suspicious server transmission.",
            scenarioText = "Your SOC monitor intercepted a mysterious Base64 encoded string ending in '='. Decode the payload to retrieve the flag.",
            simulatedLogsOrHeaders = """
[SYS_LOG] INTERCEPTED PACKET DATA:
Payload: ZmxhZ3tzYWZlX3NhbmRib3hfYmVnaW5uZXJ9
Format: Base64 Encoded
Status: Pending Decryption
            """.trimIndent(),
            hints = listOf(
                "Base64 strings use A-Z, a-z, 0-9, +, /, and padding '='.",
                "In Linux terminal: echo 'string' | base64 -d",
                "Flag format is flag{...}"
            ),
            flag = "flag{safe_sandbox_beginner}",
            xpReward = 150
        ),
        CtfChallenge(
            id = "ctf_2",
            level = LevelEnum.BEGINNER,
            title = "MD5 Weak Hash Audit",
            category = "Cryptography",
            difficulty = "Easy",
            description = "An old backup configuration file contains a unsalted MD5 hash of a weak default admin password.",
            scenarioText = "Crack the MD5 hash '5d41402abc4b2a76b9719d911017c592' or identify the word to generate the secret flag format flag{<word>_passwords_win}.",
            simulatedLogsOrHeaders = """
[CONFIG_LOG] DB_ADMIN_HASH = 5d41402abc4b2a76b9719d911017c592
[ALERT] MD5 is cryptographically broken! Fast collision attacks detected.
            """.trimIndent(),
            hints = listOf(
                "The hash '5d41402abc4b2a76b9719d911017c592' is the MD5 of the common 5-letter word 'hello'.",
                "Replace <word> in flag{strong_passwords_win} or submit flag{strong_passwords_win}.",
                "MD5 should never be used for password hashing."
            ),
            flag = "flag{strong_passwords_win}",
            xpReward = 150
        ),
        CtfChallenge(
            id = "ctf_3",
            level = LevelEnum.INTERMEDIATE,
            title = "HTTP Header Phishing Inspector",
            category = "Web Exploitation",
            difficulty = "Medium",
            description = "Analyze HTTP response headers from a simulated phishing site to locate hidden flag.",
            scenarioText = "Inspect custom server headers returned during simulated HTTP GET connection to extract the hidden X-CTF-Header.",
            simulatedLogsOrHeaders = """
HTTP/1.1 200 OK
Date: Fri, 31 Jul 2026 12:00:00 GMT
Server: nginx/1.18.0
Content-Type: text/html; charset=UTF-8
X-Powered-By: CyberHack-Sandbox
X-CTF-Header: flag{phishing_headers_exposed}
Strict-Transport-Security: max-age=31536000
            """.trimIndent(),
            hints = listOf(
                "Look at custom response headers starting with X-CTF-Header.",
                "Header fields often leak software versions or developer flags.",
                "Copy the exact flag value from X-CTF-Header."
            ),
            flag = "flag{phishing_headers_exposed}",
            xpReward = 200
        ),
        CtfChallenge(
            id = "ctf_4",
            level = LevelEnum.INTERMEDIATE,
            title = "Network Port Scan Analysis",
            category = "Network Security",
            difficulty = "Medium",
            description = "Analyze simulated Nmap port scan output to find the open high port running a vulnerability beacon.",
            scenarioText = "A target host 192.168.1.100 was scanned using Nmap. Examine the service output to identify the secret flag.",
            simulatedLogsOrHeaders = """
PORT     STATE SERVICE     VERSION
22/tcp   open  ssh         OpenSSH 8.2p1
80/tcp   open  http        Apache httpd 2.4.41
8080/tcp open  http-proxy  CyberHack-FlagServer (Flag: flag{open_port_detected})
3306/tcp closed mysql
            """.trimIndent(),
            hints = listOf(
                "Port 8080 is running a custom HTTP service banner.",
                "Version string includes the flag directly.",
                "Port scanning helps identify unhardened open services."
            ),
            flag = "flag{open_port_detected}",
            xpReward = 200
        ),
        CtfChallenge(
            id = "ctf_5",
            level = LevelEnum.ADVANCED,
            title = "SQL Injection Defense Audit",
            category = "Web Defense",
            difficulty = "Hard",
            description = "Analyze a vulnerable web SQL login script and submit the correct sanitized flag.",
            scenarioText = "Target endpoint login accepts payload `' OR '1'='1`. The defensive patch converts it to Prepared Statements. Identify the fixed flag.",
            simulatedLogsOrHeaders = """
[DEFENSE AUDIT REPORT]
Vulnerable Code: SELECT * FROM users WHERE user = '${'$'}user' AND pass = '${'$'}pass'
Patched Code: SELECT * FROM users WHERE user = ? AND pass = ?
Patch Verification Token: flag{sql_injection_sanitized_2026}
            """.trimIndent(),
            hints = listOf(
                "Parameterized queries stop string concatenation attacks.",
                "Copy the Patch Verification Token flag.",
                "Always sanitize user inputs!"
            ),
            flag = "flag{sql_injection_sanitized_2026}",
            xpReward = 250
        ),
        CtfChallenge(
            id = "ctf_6",
            level = LevelEnum.ADVANCED,
            title = "XSS CSP Sanitizer Challenge",
            category = "Web Defense",
            difficulty = "Hard",
            description = "Verify Content Security Policy (CSP) headers mitigating Stored Cross-Site Scripting (XSS).",
            scenarioText = "A web app neutralized script tag `<script>alert('xss')</script>` by setting strict CSP header `script-src 'self'`. Extract the security audit flag.",
            simulatedLogsOrHeaders = """
[CSP HEADER ENABLED]
Content-Security-Policy: default-src 'self'; script-src 'self';
[XSS BLOCKED] Script execution blocked on domain.
Audit Flag: flag{xss_mitigated_shield}
            """.trimIndent(),
            hints = listOf(
                "CSP headers restrict sources where scripts can execute.",
                "Copy the Audit Flag from the security header report.",
                "Combine input sanitization with CSP for full XSS protection."
            ),
            flag = "flag{xss_mitigated_shield}",
            xpReward = 250
        )
    )
}
