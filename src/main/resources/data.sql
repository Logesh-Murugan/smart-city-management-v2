-- Sample data for Smart City Management System
-- This file will be automatically loaded by Spring Boot

-- Insert test users with pre-encoded password (password: "test123") - ignore duplicates
-- Password encoded with BCrypt: $2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXYLFPiXkjjP4NjwjkiKKW6qtpO
INSERT IGNORE INTO users (name, username, email, password, role, active, created_at) VALUES
('Test User', 'testuser', 'test@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXYLFPiXkjjP4NjwjkiKKW6qtpO', 'ROLE_USER', true, NOW()),
('Admin User', 'admin', 'admin@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXYLFPiXkjjP4NjwjkiKKW6qtpO', 'ROLE_ADMIN', true, NOW());

-- Insert Tourist Spots - ignore duplicates
INSERT IGNORE INTO tourist_spots (name, description, image_url, location) VALUES
('Central Park', 'A beautiful park in the heart of the city with walking trails and gardens.', 'https://example.com/central-park.jpg', 'Downtown City Center'),
('City Museum', 'Historical museum showcasing the rich heritage of our smart city.', 'https://example.com/museum.jpg', '123 Heritage Street'),
('Waterfront Plaza', 'Scenic waterfront area perfect for evening walks and family activities.', 'https://example.com/waterfront.jpg', 'Marina District'),
('Tech Innovation Hub', 'Modern technology center and startup incubator.', 'https://example.com/tech-hub.jpg', '456 Innovation Avenue');

-- Insert Basic Emergency Contacts - ignore duplicates
INSERT IGNORE INTO emergency_contacts (service_name, service_type, phone_number, location, description, availability, created_at, updated_at) VALUES
('Fire Department', 'Fire', '101', 'City Fire Station', 'Emergency fire and rescue services', '24/7', NOW(), NOW()),
('Police Department', 'Police', '100', 'City Police Station', 'Emergency police services and law enforcement', '24/7', NOW(), NOW()),
('Ambulance Service', 'Medical', '102', 'City Hospital', 'Emergency medical services and ambulance', '24/7', NOW(), NOW()),
('Emergency Services', 'Emergency', '911', 'Emergency Control Room', 'General emergency services coordination', '24/7', NOW(), NOW()),
('City Emergency Hotline', 'Emergency', '1800-CITY-911', 'City Hall', 'Municipal emergency hotline for all city services', '24/7', NOW(), NOW());

-- Insert City Services - ignore duplicates
INSERT IGNORE INTO city_services (name, type, address, phone, email, timings, description, created_at, updated_at) VALUES
('City General Hospital', 'Hospital', '789 Health Street', '555-0101', 'info@cityhospital.gov', '24/7 Emergency, 8:00 AM - 6:00 PM General', 'Full-service hospital with emergency care and specialized departments.', NOW(), NOW()),
('Central Police Station', 'Police', '321 Safety Avenue', '555-0102', 'contact@citypolice.gov', '24/7 Emergency Services', '24/7 police services and community safety programs.', NOW(), NOW()),
('Waste Management Center', 'Utilities', '654 Green Way', '555-0103', 'waste@cityservices.gov', '7:00 AM - 4:00 PM Mon-Fri', 'Weekly garbage collection and recycling services for all city residents.', NOW(), NOW()),
('Public Library', 'Education', '987 Knowledge Blvd', '555-0104', 'info@citylibrary.gov', '9:00 AM - 8:00 PM Mon-Fri, 10:00 AM - 6:00 PM Sat-Sun', 'Public library with books, digital resources, and community programs.', NOW(), NOW()),
('City Hall', 'Government', '111 Civic Center', '555-0105', 'info@cityhall.gov', '8:00 AM - 5:00 PM Mon-Fri', 'Municipal government offices and citizen services.', NOW(), NOW()),
('Fire Station #1', 'Fire', '222 Rescue Road', '555-0106', 'emergency@cityfire.gov', '24/7 Emergency Response', 'Primary fire station serving downtown and surrounding areas.', NOW(), NOW()),
('City Bus Terminal', 'Transport', '333 Transit Hub', '555-0107', 'info@citytransit.gov', '5:00 AM - 11:00 PM Daily', 'Main bus terminal connecting to all city routes and regional destinations.', NOW(), NOW());

-- Insert Utility Services - ignore duplicates
INSERT IGNORE INTO utility_services (service_name, department, office_address, contact_numbers, email, working_hours, description, online_request_link, utility_type, created_at, updated_at) VALUES
('Electricity Board', 'Tamil Nadu Electricity Board (TNEB)', '123 Power House Road, Electrical Complex', '1800-425-7004, 044-2841-2345', 'complaints@tneb.gov.in', 'Mon-Sat 9:00 AM - 5:00 PM', 'Electricity supply, new connections, bill payments, and power outage complaints', 'https://www.tnebnet.org', 'ELECTRICITY', NOW(), NOW()),
('Water Supply Department', 'Chennai Metropolitan Water Supply', '456 Water Works Road, Kilpauk', '1800-425-9999, 044-2534-0234', 'complaints@chennaimetrowater.gov.in', 'Mon-Fri 9:00 AM - 4:00 PM', 'Water supply, new connections, tanker requests, and water quality issues', 'https://www.chennaimetrowater.gov.in', 'WATER', NOW(), NOW()),
('Waste Management Corporation', 'Greater Chennai Corporation', '789 Corporation Building, Ripon Street', '1913, 044-2538-4520', 'swm@chennaicorporation.gov.in', 'Mon-Sat 8:00 AM - 6:00 PM', 'Garbage collection, waste disposal, recycling programs, and sanitation services', NULL, 'WASTE', NOW(), NOW()),
('Street Light Maintenance', 'Electrical Division - Corporation', '321 Municipal Office, Anna Salai', '044-2846-0000, 1913', 'streetlight@chennaicorporation.gov.in', '24/7 Complaint Hotline', 'Street light repairs, new installations, and electrical maintenance', NULL, 'STREET_LIGHT', NOW(), NOW()),
('Sewage Treatment Board', 'Chennai Metropolitan Development Authority', '654 CMDA Building, Egmore', '044-2841-7896, 1800-425-1234', 'sewage@cmdachennai.gov.in', 'Mon-Fri 9:00 AM - 5:00 PM', 'Sewage treatment, drainage issues, and underground drainage maintenance', NULL, 'SEWAGE', NOW(), NOW()),
('Gas Distribution Company', 'Bharat Gas Corporation', '987 Gas Godown, Madhavaram', '1800-233-3555, 044-2594-5678', 'customer.care@bharatgas.co.in', 'Mon-Sat 9:00 AM - 6:00 PM', 'LPG connections, cylinder booking, gas leak complaints, and safety inspections', 'https://www.bharatgas.co.in', 'GAS', NOW(), NOW()),
('Broadband Services', 'BSNL Broadband Division', '147 Telecom Bhavan, T. Nagar', '1800-345-1500, 044-2434-1234', 'broadband@bsnl.co.in', 'Mon-Sat 10:00 AM - 6:00 PM', 'Internet connections, broadband issues, speed complaints, and technical support', 'https://www.bsnl.co.in', 'INTERNET', NOW(), NOW()),
('Emergency Power Supply', 'State Electricity Emergency Services', '555 Emergency Power Station, Guindy', '1800-425-POWER, 044-2220-1100', 'emergency@tneb.gov.in', '24/7 Emergency Service', 'Emergency power restoration, transformer failures, and critical power issues', NULL, 'ELECTRICITY', NOW(), NOW()),
('Municipal Water Tanker', 'Chennai Corporation Water Division', '888 Water Tanker Depot, Perambur', '1916, 044-2534-TANK', 'watertanker@chennaicorporation.gov.in', '24/7 Emergency Service', 'Emergency water supply through tankers, water shortage areas, and bulk water supply', NULL, 'WATER', NOW(), NOW()),
('Hazardous Waste Management', 'Tamil Nadu Pollution Control Board', '999 Environmental Complex, Guindy', '044-2235-4455, 1800-425-GREEN', 'hazwaste@tnpcb.gov.in', 'Mon-Fri 9:00 AM - 5:00 PM', 'Industrial waste disposal, hazardous material collection, and environmental compliance', 'https://www.tnpcb.gov.in', 'WASTE', NOW(), NOW()),
('Highway Street Lighting', 'National Highways Authority', '777 Highway Maintenance Office, Porur', '1033, 044-2476-8899', 'lighting@nhai.gov.in', 'Mon-Sat 8:00 AM - 8:00 PM', 'Highway street light maintenance, toll road lighting, and traffic signal repairs', NULL, 'STREET_LIGHT', NOW(), NOW()),
('Industrial Sewage Treatment', 'Tamil Nadu Industrial Development Corporation', '333 Industrial Estate, Ambattur', '044-2625-7788, 1800-425-TIDCO', 'sewage@tidco.com', 'Mon-Fri 9:00 AM - 6:00 PM', 'Industrial wastewater treatment, effluent management, and industrial drainage systems', NULL, 'SEWAGE', NOW(), NOW()),
('Piped Natural Gas', 'Indraprastha Gas Limited', '222 Gas Distribution Center, Sholinganallur', '1800-425-4427, 044-4567-8900', 'png@igl.co.in', 'Mon-Sat 9:00 AM - 7:00 PM', 'Piped natural gas connections, PNG meter installations, and gas pipeline maintenance', 'https://www.iglonline.net', 'GAS', NOW(), NOW()),
('Fiber Optic Internet', 'Railtel Corporation', '111 Railway Telecom Building, Central Station', '1800-103-3636, 044-2819-4567', 'fiber@railtelindia.com', 'Mon-Sat 9:00 AM - 6:00 PM', 'High-speed fiber internet, enterprise connectivity, and railway communication services', 'https://www.railtelindia.com', 'INTERNET', NOW(), NOW());

-- Insert Sample Utility Requests - ignore duplicates
INSERT IGNORE INTO utility_requests (citizen_name, mobile_number, email, address, service_type, complaint_type, description, preferred_contact_method, status, utility_service_id, created_at, updated_at) VALUES
('Rajesh Kumar', '9876543210', 'rajesh.kumar@email.com', '123 Anna Nagar, Chennai - 600040', 'ELECTRICITY', 'Power outage in residential area', 'There has been no electricity in our street for the past 6 hours. Multiple houses are affected. The transformer seems to be making unusual sounds before it went off.', 'PHONE', 'RECEIVED', 1, DATE_SUB(NOW(), INTERVAL 2 HOUR), DATE_SUB(NOW(), INTERVAL 2 HOUR)),
('Priya Sharma', '9123456789', 'priya.sharma@gmail.com', '456 T. Nagar, Chennai - 600017', 'WATER', 'No water supply for 3 days', 'Our apartment complex has not received water supply for the past 3 days. We have contacted the local office but no response. Urgent tanker service required for 200 families.', 'EMAIL', 'IN_PROGRESS', 2, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 4 HOUR)),
('Mohammed Ali', '9988776655', NULL, '789 Velachery, Chennai - 600042', 'WASTE', 'Garbage not collected for a week', 'The garbage collection truck has not come to our street for over a week. The waste is piling up and creating health hazards. Stray dogs are spreading the garbage around.', 'PHONE', 'RESOLVED', 3, DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
('Lakshmi Devi', '9445566778', 'lakshmi.devi@yahoo.com', '321 Adyar, Chennai - 600020', 'STREET_LIGHT', 'Street lights not working', 'All street lights on our road have been non-functional for 2 weeks. This is causing safety issues especially for women and elderly people during night time.', 'BOTH', 'IN_PROGRESS', 4, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
('Suresh Babu', '9876512345', 'suresh.babu@hotmail.com', '654 Tambaram, Chennai - 600045', 'SEWAGE', 'Sewage overflow in residential area', 'The main sewage line in our area is overflowing for the past 4 days. The entire street is flooded with sewage water. Immediate attention required as it is causing health issues.', 'PHONE', 'RECEIVED', 5, DATE_SUB(NOW(), INTERVAL 6 HOUR), DATE_SUB(NOW(), INTERVAL 6 HOUR)),
('Kavitha Raman', '9123987654', 'kavitha.raman@gmail.com', '987 Chrompet, Chennai - 600044', 'GAS', 'Gas leak in residential pipeline', 'We smell gas leak near our house from the main pipeline. This is very dangerous and needs immediate attention. Multiple neighbors have reported the same issue.', 'BOTH', 'RESOLVED', 6, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
('Arjun Krishnan', '9876598765', NULL, '147 Porur, Chennai - 600116', 'INTERNET', 'Broadband connection down', 'Our broadband internet connection has been down for 3 days. Work from home is affected. Multiple complaints to customer care but no technician has visited yet.', 'PHONE', 'IN_PROGRESS', 7, DATE_SUB(NOW(), INTERVAL 8 HOUR), DATE_SUB(NOW(), INTERVAL 3 HOUR)),
('Meera Nair', '9445512378', 'meera.nair@outlook.com', '258 Besant Nagar, Chennai - 600090', 'ELECTRICITY', 'Frequent power cuts', 'Our area experiences power cuts 4-5 times daily for the past week. Each cut lasts 2-3 hours. This is affecting our daily routine and work. Please provide a permanent solution.', 'EMAIL', 'RECEIVED', 1, DATE_SUB(NOW(), INTERVAL 12 HOUR), DATE_SUB(NOW(), INTERVAL 12 HOUR)),
('Ravi Chandran', '9876123456', 'ravi.chandran@gmail.com', '369 Mylapore, Chennai - 600004', 'WATER', 'Water quality issues', 'The water supplied to our area has a bad smell and taste. Many residents are falling sick. We suspect contamination in the main supply line. Please test and fix immediately.', 'BOTH', 'IN_PROGRESS', 2, DATE_SUB(NOW(), INTERVAL 18 HOUR), DATE_SUB(NOW(), INTERVAL 6 HOUR)),
('Deepa Menon', '9123654789', NULL, '741 Sholinganallur, Chennai - 600119', 'WASTE', 'Request for additional garbage bins', 'Our street needs more garbage bins as the current ones are insufficient for the growing population. Residents are forced to dump waste on the road. Please install 3-4 more bins.', 'PHONE', 'RECEIVED', 3, DATE_SUB(NOW(), INTERVAL 3 HOUR), DATE_SUB(NOW(), INTERVAL 3 HOUR));

-- Insert Additional Tourist Spots - ignore duplicates
INSERT IGNORE INTO tourist_spots (name, description, location, category, rating, image_url, visiting_hours, entry_fee, latitude, longitude, created_at, updated_at) VALUES
('Kapaleeshwarar Temple', 'Ancient Shiva temple in Mylapore, known for its Dravidian architecture and vibrant festivals. One of the oldest temples in Chennai with rich cultural significance.', 'Mylapore, Chennai - 600004', 'Religious', 4.6, 'https://example.com/kapaleeshwarar.jpg', '5:30 AM - 12:00 PM, 4:00 PM - 9:00 PM', 'Free', 13.0339, 80.2619, NOW(), NOW()),
('Government Museum', 'One of the oldest museums in India, housing an extensive collection of archaeological artifacts, art, and natural history specimens.', 'Egmore, Chennai - 600008', 'Museum', 4.3, 'https://example.com/govt-museum.jpg', '9:30 AM - 5:00 PM (Closed on Fridays)', '₹15 for adults, ₹10 for children', 13.0732, 80.2609, NOW(), NOW()),
('Elliot Beach', 'Peaceful beach in Besant Nagar, perfect for morning walks and evening relaxation. Less crowded than Marina Beach with clean surroundings.', 'Besant Nagar, Chennai - 600090', 'Beach', 4.2, 'https://example.com/elliot-beach.jpg', '24 hours', 'Free', 12.9996, 80.2668, NOW(), NOW()),
('Santhome Cathedral', 'Historic Roman Catholic cathedral built over the tomb of St. Thomas the Apostle. Beautiful Gothic architecture and peaceful atmosphere.', 'Santhome, Chennai - 600004', 'Religious', 4.4, 'https://example.com/santhome.jpg', '6:00 AM - 8:00 PM', 'Free', 13.0244, 80.2784, NOW(), NOW()),
('Guindy National Park', 'Urban national park in the heart of Chennai, home to various wildlife species including deer, jackals, and numerous bird species.', 'Guindy, Chennai - 600032', 'Natural', 4.1, 'https://example.com/guindy-park.jpg', '9:00 AM - 5:30 PM', '₹10 for adults, ₹5 for children', 13.0067, 80.2206, NOW(), NOW()),
('Express Avenue Mall', 'Premier shopping and entertainment destination with international brands, food courts, and multiplex cinema.', 'Royapettah, Chennai - 600014', 'Shopping', 4.0, 'https://example.com/express-avenue.jpg', '10:00 AM - 10:00 PM', 'Free entry', 13.0594, 80.2584, NOW(), NOW()),
('Theosophical Society', 'Peaceful 260-acre campus with beautiful gardens, ancient trees, and the famous Adyar Banyan Tree. Perfect for nature lovers.', 'Adyar, Chennai - 600020', 'Cultural', 4.3, 'https://example.com/theosophical.jpg', '8:30 AM - 10:00 AM, 2:00 PM - 5:00 PM', '₹3 per person', 13.0067, 80.2479, NOW(), NOW()),
('Dakshinachitra', 'Living museum showcasing South Indian heritage, traditional crafts, and cultural performances. Great for understanding regional culture.', 'Muttukadu, Chennai - 603112', 'Cultural', 4.5, 'https://example.com/dakshinachitra.jpg', '10:00 AM - 6:00 PM (Closed on Tuesdays)', '₹175 for adults, ₹100 for children', 12.8449, 80.2321, NOW(), NOW());

-- Insert Additional Emergency Contacts - ignore duplicates
INSERT IGNORE INTO emergency_contacts (service_name, service_type, phone_number, alternate_number, location, description, availability, created_at, updated_at) VALUES
('Chennai Police Control Room', 'Police', '100', '044-28447701', 'Commissioner Office, Egmore', 'Main police control room for all emergency situations, crime reporting, and immediate police assistance', '24/7', NOW(), NOW()),
('Tamil Nadu Fire & Rescue Services', 'Fire', '101', '044-28520999', 'Fire Station, Anna Salai', 'Fire emergency services, rescue operations, and disaster management. Equipped with modern firefighting equipment', '24/7', NOW(), NOW()),
('Government General Hospital', 'Medical', '108', '044-25305000', 'Park Town, Chennai - 600003', 'Primary government hospital with emergency medical services, trauma care, and ambulance services', '24/7', NOW(), NOW()),
('Apollo Hospital Emergency', 'Medical', '1066', '044-28296000', 'Greams Road, Chennai - 600006', 'Private hospital emergency services with advanced medical facilities and specialist doctors', '24/7', NOW(), NOW()),
('Chennai Traffic Police', 'Police', '103', '044-28447777', 'Traffic Police Headquarters, Egmore', 'Traffic-related emergencies, accident reporting, and road safety assistance', '24/7', NOW(), NOW()),
('Disaster Management Cell', 'Emergency', '1077', '044-28520100', 'Ripon Building, Chennai - 600003', 'Natural disaster response, flood management, and emergency evacuation services', '24/7', NOW(), NOW()),
('Women Helpline', 'Police', '1091', '044-28447890', 'All Women Police Stations', 'Dedicated helpline for women in distress, domestic violence, and women safety issues', '24/7', NOW(), NOW()),
('Child Helpline', 'Emergency', '1098', '044-28520200', 'Child Welfare Committee Office', 'Emergency assistance for children in need, child abuse reporting, and child protection services', '24/7', NOW(), NOW()),
('Ambulance Service (GVK EMRI)', 'Medical', '108', '044-71077108', 'Multiple locations across Chennai', 'Free ambulance service with trained paramedics and emergency medical equipment', '24/7', NOW(), NOW()),
('Chennai Corporation Helpline', 'Emergency', '1913', '044-25619200', 'Ripon Building, Chennai - 600003', 'Municipal services, water supply issues, garbage collection, and civic emergencies', '24/7', NOW(), NOW());

-- Insert Sample Feedback - ignore duplicates
INSERT IGNORE INTO feedback (name, email, phone, subject, message, rating, feedback_type, created_at, updated_at) VALUES
('Rajesh Kumar', 'rajesh.kumar@email.com', '9876543210', 'Excellent Public Transport', 'The new metro service has greatly improved connectivity in the city. Very satisfied with the cleanliness and punctuality.', 5, 'COMPLIMENT', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
('Priya Sharma', 'priya.sharma@email.com', '9123456789', 'Street Light Issues', 'Several street lights in Anna Nagar are not working for the past week. This is causing safety concerns for residents.', 2, 'COMPLAINT', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
('Arjun Patel', 'arjun.patel@email.com', '9988776655', 'Suggestion for Park Improvement', 'The city parks could benefit from more benches and better playground equipment for children. Overall maintenance is good.', 4, 'SUGGESTION', DATE_SUB(NOW(), INTERVAL 3 HOUR), DATE_SUB(NOW(), INTERVAL 3 HOUR)),
('Meera Nair', 'meera.nair@email.com', '9876512345', 'Water Supply Feedback', 'Water supply has been consistent in our area. Quality is also good. Thank you for the reliable service.', 4, 'GENERAL', DATE_SUB(NOW(), INTERVAL 5 HOUR), DATE_SUB(NOW(), INTERVAL 5 HOUR)),
('Vikram Singh', 'vikram.singh@email.com', '9123987654', 'Waste Collection Complaint', 'Garbage collection in our street has been irregular. Sometimes waste is not collected for 2-3 days.', 2, 'COMPLAINT', DATE_SUB(NOW(), INTERVAL 6 HOUR), DATE_SUB(NOW(), INTERVAL 6 HOUR));
-- INSERT INTO feedback (user_id, service_id, rating, comment, timestamp) VALUES
-- (1, 1, 5, 'Excellent hospital service! Very professional staff.', '2024-01-15 10:30:00'),
-- (2, 3, 4, 'Waste collection is very reliable and on time.', '2024-01-16 14:20:00'),
-- (3, 4, 5, 'Great library with excellent digital resources.', '2024-01-17 09:15:00'),
-- (1, 2, 4, 'Police response was quick and professional.', '2024-01-18 16:45:00');
