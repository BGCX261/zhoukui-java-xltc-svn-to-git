package org.sunney.college.designer.mvc;

public interface BeatModelInterface {
	void initialize();
	void on();
	void off();
	void setBPM(int bpm);
	void getBPM();
	void registerObserver(BeatObserver bo);
	void removeObserver(BeatObserver bo);
	void registerObserver(BMPObserver bo);
	void removeObserver(BMPObserver bo);
}
