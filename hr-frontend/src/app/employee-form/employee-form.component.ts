import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee, EmployeeService } from '../employee.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee-form.component.html',
  styleUrl: './employee-form.component.css'
})
export class EmployeeFormComponent implements OnInit {
  employee: Employee = {
    id: 0,
    firstName: '',
    lastName: '',
    title: '',
    division: '',
    building: '',
    room: ''
  };

  isEdit = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private employeeService: EmployeeService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.employeeService.get(+id).subscribe(data => this.employee = data);
    }
  }

  onSubmit(): void {
    if (this.isEdit) {
      this.employeeService.update(this.employee.id, this.employee).subscribe(() => {
        this.router.navigate(['/']);
      });
    } else {
      this.employeeService.add(this.employee).subscribe(() => {
        this.router.navigate(['/']);
      });
    }
  }
}
