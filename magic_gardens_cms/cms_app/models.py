from django.db import models

# Create your models here.
class general_info(models.Model):
    app_name = models.CharField(max_length=200)
    contact_header = models.CharField(max_length=200)
    contact_address = models.CharField(max_length=200)
    contact_phone = models.CharField(max_length=200)
    contact_email = models.CharField(max_length=200)
    contact_website = models.CharField(max_length=200)
    hours_header = models.CharField(max_length=200)
    hours_summer_header = models.CharField(max_length=200)
    hours_summer = models.CharField(max_length=200)
    hours_winter_header = models.CharField(max_length=200)
    hours_winter = models.CharField(max_length=200)
    hours_holiday_header = models.CharField(max_length=200)
    hours_holiday = models.CharField(max_length=200)
    hours_note = models.CharField(max_length=200)
    admission_header = models.CharField(max_length=200)
    admission_ga_header = models.CharField(max_length=200)
    admission_ga_rates = models.CharField(max_length=200)
    admission_ga_info = models.CharField(max_length=200)
    admission_tours_header = models.CharField(max_length=200)
    admission_tours_neigh = models.CharField(max_length=200)
    admission_tours_neigh_info = models.CharField(max_length=200)
    admission_tours_site = models.CharField(max_length=200)
    admission_tours_site_info = models.CharField(max_length=200)
    admission_tours_info = models.CharField(max_length=200)
    policy_header = models.CharField(max_length=200)
    policy_info = models.CharField(max_length=200)
    parking_header = models.CharField(max_length=200)
    parking_info = models.CharField(max_length=200)
    accessibility_header = models.CharField(max_length=200)
    accessibility_info = models.CharField(max_length=200)


	
class point_of_interest(models.Model):
    poi_object = models.CharField(max_length=200)
    poi_info = models.CharField(max_length=200)
    poi_location = models.CharField(max_length=200)
    poi_theme = models.CharField(max_length=200)
    poi_photo = models.CharField(max_length=200)
