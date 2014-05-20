package com.example.paypro;

import java.util.List;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paypro.data.Group;
import com.example.paypro.data.Report;
import com.example.paypro.data.ReportData;
import com.example.paypro.tasks.ReportGenerationTask;

public class ReportActivity extends Activity {

	private Group group;
	private Report report;
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);

		layout = (LinearLayout) findViewById(R.id.reportView);

		group = (Group) getIntent().getSerializableExtra("group");
		report = new Report();
		report.setGroupId(group.getId());

		ReportGenerationTask task = new ReportGenerationTask(this, this);

		task.execute(new Report[] { report });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report, menu);
		return false;
	}

	public void setReport(Report report) {
		this.report = report;
		updateReport();

	}

	private void updateReport() {

		if (report == null) {
			return;
		}

		// View view = new View(this);
		TextView view = new TextView(this);
		view.setText("Total Expenses: " + report.getTotalExpenses());
		layout.addView(view);

		TextView expensesPerHead = new TextView(this);
		expensesPerHead.setText("Expenses Per Head: "
			+ report.getExpensePerHead());
		layout.addView(expensesPerHead);

		TextView details = new TextView(this);
		details.setText("Details");
		layout.addView(details);

		List<ReportData> details2 = report.getDetails();
		TextView name[] = new TextView[details2.size()];
		TextView amountToPay[] = new TextView[details2.size()];
		TextView amountSpent[] = new TextView[details2.size()];
		for (int iter = 0; iter < details2.size(); iter++) {
			System.out.println(details2.get(iter).toString());
			name[iter] = new TextView(this);
			name[iter].setText(details2.get(iter).getUserName());
			layout.addView(name[iter]);
			
			amountToPay[iter] = new TextView(this);
			amountToPay[iter].setText("Amount to pay: Rs. "
				+ details2.get(iter).getAmountDues() + "/-");
			layout.addView(amountToPay[iter]);
			
			amountSpent[iter] = new TextView(this);
			amountSpent[iter].setText("Amount spent: Rs. "
				+ details2.get(iter).getAmountPaid() + "/-");
			layout.addView(amountSpent[iter]);
			
			View view1 = new View(this);
			view1.setBackgroundColor(color.black);
			view1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 2));
			layout.addView(view1);
		}
		layout.refreshDrawableState();
	}
}
