DROP TABLE EventsLog;
DROP TABLE CourseLog;

CREATE TABLE EventsLog
(
	ID					INTEGER,
	Event				VARCHAR(10) NOT NULL,
	WkstName			VARCHAR(20) NOT NULL,
	Role				VARCHAR(10) NOT NULL,
	ClassCourseNum		VARCHAR(8)  NOT NULL,
	ClassCourseSec		INTEGER,
	ReqTime				TIMESTAMP        NOT NULL,
	WaitTime			INTERVAL DAY TO SECOND,
    CancelTime          TIMESTAMP,
	PRIMARY KEY (ID)
);



CREATE TABLE CourseLog
(
	ID					INTEGER,
	Course				VARCHAR(10) NOT NULL,
	Section			VARCHAR(20) NOT NULL,
	Day				VARCHAR(10) NOT NULL,
	StartDate		TIMESTAMP        NOT NULL,
	EndDate			TIMESTAMP,
	StartTime				TIMESTAMP,
	EndTime		   	 TIMESTAMP,
	PRIMARY KEY (ID)
);

/*
These top two works
INSERT INTO EventsLog VALUES(1, 'Help', 'Workstation02', 'Client', 'CS 260', 02, TO_DATE('2012/06/22 14:22:35', 'yyyy/mm/dd HH24:mi:ss'), null, null);
INSERT INTO EventsLog VALUES(2, 'Cancel', 'Workstation02', 'Client', 'CS 260', 02, TO_DATE('2012/06/22 14:22:35', 'yyyy/mm/dd HH24:mi:ss'), INTERVAL '02:00' MINUTE TO SECOND, TO_TIMESTAMP('14:35:35', 'HH24:mi:ss'));*/


/*INSERT INTO EventsLog VALUES(3, 'Cancel', 'Workstation02', 'Admin', 'CS 260', 02, TO_TIMESTAMP('2012/06/22 14:22:35', 'yyyy/mm/dd HH24:mi:ss.FF3'), INTERVAL '0 14:33:35' DAY TO SECOND);*/
/*INSERT INTO EventsLog VALUES(3, 'Cancel', 'Workstation02', 'Admin', 'CS 260', 02, TO_TIMESTAMP('14:22:35', 'HH24:mi:ss.FF3'), INTERVAL '02:00' MINUTE TO SECOND, TO_TIMESTAMP('14:35:35', 'HH24:mi:ss.FF3'));*/

/*UPDATE EventsLog SET CancelTime =
