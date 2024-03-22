#!/bin/bash

# Get the current date and time down to milliseconds
current_datetime=$(date "+%Y%m%d%H%M%S" | cut -b1-17)

# Read the name from the user
read -p "Enter a name for the migration: " name

# Generate the filename by concatenating the name and the current datetime
filename="V1__${current_datetime}__${name}.sql"

migration_root_dir="migrations"
touch "${migration_root_dir}/${filename}"

# Print the generated filename
echo "Generated filename: ${migration_root_dir}/${filename}"

